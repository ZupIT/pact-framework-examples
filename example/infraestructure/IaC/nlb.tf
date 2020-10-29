resource "aws_lb_target_group_attachment" "pact" {
  target_group_arn = aws_lb_target_group.pact.arn
  target_id        = aws_instance.default.id
  port             = 80
}


resource "aws_lb_target_group" "pact" {
  name          = "tg-${var.aws_application_name}"
  port          = 80
  protocol      = "TCP"
  target_type   = "instance"
  vpc_id        = var.vpc_id

  stickiness {
        enabled = false
        type = "lb_cookie"
    }
}

resource "aws_lb_listener" "nlb" {
  load_balancer_arn = aws_lb.nlb.id
  port              = "80"
  protocol          = "TCP"

  default_action {
    target_group_arn = aws_lb_target_group.pact.id
    type             = "forward"
  }
}

resource "aws_lb" "nlb" {
  name               = "nlb-${var.aws_application_name}"
  internal           = true
  load_balancer_type = "network"
  subnets            = local.subnet_ids_list

  enable_deletion_protection = false

}