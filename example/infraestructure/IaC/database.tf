resource "aws_security_group" "database" {

  name        = "sg_rds_db_${var.aws_db_cfg_engine}"
  description = "AWS security group for ${var.aws_db_cfg_engine} Database"
  vpc_id      = var.vpc_id

  ingress {
    from_port   = var.aws_db_cfg_ingress_port
    to_port     = var.aws_db_cfg_ingress_port
    protocol    = "tcp"
    cidr_blocks = [var.aws_db_cfg_cidr_block]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    application = var.aws_application_name
  }
}

resource "aws_db_instance" "default" {
  identifier             = var.aws_db_cfg_identifier
  allocated_storage      = var.aws_db_cfg_allocated_storage
  engine                 = var.aws_db_cfg_engine
  engine_version         = var.aws_db_cfg_engine_version
  instance_class         = var.aws_db_cfg_instance_class
  name                   = var.aws_db_cfg_name
  username               = var.aws_db_cfg_username
  password               = var.aws_db_cfg_password
  vpc_security_group_ids = [aws_security_group.database.id]
  db_subnet_group_name   = aws_db_subnet_group.database.id
}

resource "aws_db_subnet_group" "database" {
  name        = "rds-subgrp-${var.aws_db_cfg_name}"
  description = "Subnet group created for the RDS ${var.aws_db_cfg_name}"
  subnet_ids  = local.subnet_ids_list
}