package edu.depauw.scales.graphics

import Base._

case class Styled(graphic: Graphic, style: Style) extends Graphic {
  def bounds: Bounds = graphic.bounds

  def render(ctx: GraphicsContext): Unit = {
    ctx.save()
    style(ctx)
    graphic.render(ctx)
    ctx.restore()
  }
}

sealed trait Style {
  def apply(ctx: GraphicsContext): Unit
}

case class FillColor(c: Color) extends Style {
  def apply(ctx: GraphicsContext): Unit = {
    ctx.fillStyle = c.toString
  }
}

case class StrokeColor(c: Color) extends Style {
  def apply(ctx: GraphicsContext): Unit = {
    ctx.strokeStyle = c.toString
  }
}

case class StrokeWidth(w: Double) extends Style {
  def apply(ctx: GraphicsContext): Unit = {
    ctx.lineWidth = w
  }
}

// TODO line cap, join, and dash styles? patterns or gradients?