terraform {
  required_providers {
    aws = {
      source = "hashicorp/aws"
    }
  }
}

provider "aws" {
  profile = var.aws_profile
  region  = var.aws_region
}

data "aws_subnet_ids" "vpc-public-subnets" {
  vpc_id = var.vpc_id
  filter {
      name = "tag:Tier"
      values = ["Public"]
  }

}

resource random_id index {
  byte_length = 2
}

locals {
    subnet_ids_list = tolist(data.aws_subnet_ids.vpc-public-subnets.ids)
    subnet_ids_random_index = random_id.index.dec % length(data.aws_subnet_ids.vpc-public-subnets.ids)
    selected_subnet_id = local.subnet_ids_list[local.subnet_ids_random_index]
}


resource "aws_security_group" "default" {

    name = var.aws_security_group.sg_name
    description = "AWS security group for Pact Broker"
    vpc_id = var.vpc_id

    ingress {
        from_port   = 80
        to_port     = 80
        protocol    = "tcp"
        cidr_blocks = [ "0.0.0.0/0" ]
    }
    
    ingress {
        from_port   = 22
        to_port     = 22
        protocol    = "tcp"
        cidr_blocks = [ "0.0.0.0/0" ]
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

resource "aws_instance" "default" {
    depends_on              = [aws_db_instance.default]
    ami                     = var.aws_amis[var.aws_region]
    instance_type           = "t2.micro"
    vpc_security_group_ids  = [aws_security_group.default.id]
    subnet_id               = local.selected_subnet_id
    tags = {
        Name = "pact-broker-instance"
    }

    connection {
        host = self.private_ip
        user = var.aws_instance_user
        private_key = file(var.aws_key_path)
    }

    key_name = var.aws_key_name

    provisioner "remote-exec" {
        inline = [
            "sudo yum update -y",
            "sudo amazon-linux-extras install docker -y",
            "sudo service docker start",
            "sudo usermod -a -G docker ${var.aws_instance_user}",
            "sudo docker run -d -p 80:9292 -e PACT_BROKER_PORT=9292 -e PACT_BROKER_DATABASE_URL=postgres://${aws_db_instance.default.username}:${aws_db_instance.default.password}@${aws_db_instance.default.endpoint}/${aws_db_instance.default.name} -e PACT_BROKER_LOG_LEVEL=INFO -e PACT_BROKER_SQL_LOG_LEVEL=INFO --restart always pactfoundation/pact-broker:2"
        ]
    }
}


