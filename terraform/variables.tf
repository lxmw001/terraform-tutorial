variable "aws_access_key" {
  # set aws access key
  default = ""
}

variable "aws_secret_key" {
  # set aws secret key
  default = ""
}

variable "region" {
  # set aws region
  default = ""
}

variable "lambda_payload_filename" {
  default = "target/ejercicio1-1.0-SNAPSHOT.jar"
}

variable "createContact_function_handler" {
  default = "SaveContactHandler"
}

variable "getContact_function_handler" {
  default = "GetContactHandler"
}

variable "dynamoDBTrigger_function_handler" {
  default = "DynamoDBTriggerHandler"
}

variable "processContact_function_handler" {
  default = "ProcessContactHandler"
}

variable "lambda_runtime" {
  default = "java8"
}

variable "api_path" {
  default = "{proxy+}"
}

variable "api_env_stage_name" {
  default = "terraform-lambda-java-stage"
}