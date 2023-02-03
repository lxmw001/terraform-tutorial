resource "aws_lambda_function" "process_java_lambda_function" {
  runtime          = var.lambda_runtime
  filename         = var.lambda_payload_filename
  source_code_hash = filebase64sha256(var.lambda_payload_filename)
  function_name    = "processContactLuisTF"
  handler          = "handler.${var.processContact_function_handler}"
  timeout          = 60
  memory_size      = 256
  role             = "${aws_iam_role.iam_role_for_lambda.arn}"
}

//to check lambda exec logs
resource "aws_cloudwatch_log_group" "process-lambda-cloudwatch-logs" {
  name = "/aws/lambda/${aws_lambda_function.process_java_lambda_function.function_name}"
  retention_in_days = 3
}

//allow sns to call lambda
resource "aws_lambda_permission" "allow-sns-to-lambda" {
  function_name = aws_lambda_function.process_java_lambda_function.function_name
  action = "lambda:InvokeFunction"
  principal = "sns.amazonaws.com"
  source_arn = "arn:aws:sns:us-east-1:161142984839:ContactsTopicLuis"
  statement_id = "AllowExecutionFromSNS"
}

//lambda subscribes the topic, so it should be notified when other resource publishes to the topic
resource "aws_sns_topic_subscription" "sns-lambda-subscritption" {
  topic_arn = "arn:aws:sns:us-east-1:161142984839:ContactsTopicLuis"
  protocol = "lambda"
  endpoint = aws_lambda_function.process_java_lambda_function.arn
}
