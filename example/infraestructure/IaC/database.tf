resource "aws_security_group" "database" {

    name = var.aws_security_group.sg_name_db
    description = "AWS security group for Postgres Database"
    vpc_id = var.vpc_id

    ingress {
        from_port   = 5342
        to_port     = 5342
        protocol    = "tcp"
        cidr_blocks = [ "172.23.0.0/16" ]
    }
    
    egress {
        from_port   = 0
        to_port     = 0
        protocol    = "-1"
        cidr_blocks = ["0.0.0.0/0"]
    }

    tags = {
        application = var.aws_tags
    }
}

resource "aws_db_instance" "default" {
    identifier             = "pact-broker-db-postgres"
    allocated_storage      = "20"
    engine                 = "postgres"
    engine_version         = "12.3"
    instance_class         = "db.t2.micro"
    name                   = "pact"
    username               = "pact"
    password               = "PassPact"
    vpc_security_group_ids = [aws_security_group.database.id]
    db_subnet_group_name   = aws_db_subnet_group.database.id
}

resource "aws_db_subnet_group" "database" {
    name        = "rds-subgrp-pact"
    description = "Subnet group created for the RDS of the Pact Broker "
    subnet_ids  = local.subnet_ids_list
}