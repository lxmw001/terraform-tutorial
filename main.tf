locals {
  owner = "luisg"
  stack = "terraform-lambda-java"
  name = "terraform-lambda-java"
}

# terraform modules
module "ejercicio_java_lambda" {
  source = "./terraform/"
}