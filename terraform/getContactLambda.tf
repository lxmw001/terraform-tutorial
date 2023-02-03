resource "aws_lambda_function" "get_java_lambda_function" {
  runtime          = var.lambda_runtime
  filename      = var.lambda_payload_filename
  source_code_hash = filebase64sha256(var.lambda_payload_filename)
  function_name = "getContactsLuisTF"

  handler          = "handler.${var.getContact_function_handler}"
  timeout = 60
  memory_size = 256
  role             = "${aws_iam_role.iam_role_for_lambda.arn}"
}

//to check lambda exec logs
resource "aws_cloudwatch_log_group" "get-lambda-cloudwatch-logs" {
  name = "/aws/lambda/${aws_lambda_function.get_java_lambda_function.function_name}"
  retention_in_days = 1
}
