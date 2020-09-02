variable "aws_key_name" {}
variable "aws_key_path" {}

variable "aws_application_name" {
  default     = "pact-broker"
  description = "Application name description "
}

variable "aws_instance_user" {
  default = "ec2-user"
  description = "Username to ssh into the EC2 instance and install the dependencies"
}

variable "aws_region" {
  type    = string
  default = "us-east-1"
  description = "region which the resource will be created/updated"
}

variable "aws_profile" {
  type        = string
  default     = "my-profile"
  description = "Profile to use if the aws credential is not configured on default profile"
}

variable "aws_tags" {
  default = "pact-broker"
}

variable "vpc_id" {
  default     = "default"
  description = "VPC id where the resources should be created/update"
}

variable "aws_amis" {
  default = {
    us-east-1 = "ami-02354e95b39ca8dec"
    sa-east-1 = "ami-018ccfb6b4745882a"
  }
  description = "Image registry that should be used for creating the EC2 instance"
}

variable "aws_ec2_security_group" {
  default = "sg_pact_broker_rule"
  description = "Security group name og the EC2 instance"
}

variable "aws_subnet_by_names" {
  default   = ["pub-a", "pub-c"]
}