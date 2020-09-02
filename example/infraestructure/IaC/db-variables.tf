variable "aws_db_cfg_identifier" {
  default = "pact-broker-db-postgres"
}

variable "aws_db_cfg_allocated_storage" {
  type    = number
  default = 20
}

variable "aws_db_cfg_engine" {
  default = "postgres"
}

variable "aws_db_cfg_engine_version" {
  default = "12.3"
}

variable "aws_db_cfg_instance_class" {
  default = "db.t2.micro"
}

variable "aws_db_cfg_name" {
  default = "pact"
}

variable "aws_db_cfg_username" {
  default = "pact"
}

variable "aws_db_cfg_password" {
  default = "pass"
}

variable "aws_db_cfg_ingress_port" {
  type    = number
  default = 5432
}

variable "aws_db_cfg_cidr_block" {
  default = "10.0.0.0/16"
}

