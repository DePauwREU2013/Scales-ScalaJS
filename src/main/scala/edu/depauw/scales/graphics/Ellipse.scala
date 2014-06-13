package edu.depauw.scales.graphics

import Base._

case class Ellipse(width: Double, height: Double) extends Graphic {
  val bounds: Bounds = RectBounds(0, width, 0, height)

  def render(ctx: GraphicsContext): Unit = {
    fillOval(ctx, 0, 0, width, height)
    strokeOval(ctx, 0, 0, width, height)
  }

  // These are here temporarily to fill in for omissions in HTML5's Canvas...
  private def strokeOval(ctx: GraphicsContext, x: Double, y: Double, w: Double, h: Double): Unit = {
    ctx.save()
    ctx.translate(x, y)
    ctx.scale(w / h, 1)
    ctx.beginPath()
    ctx.arc(h / 2, h / 2, h / 2, 0, 2 * math.Pi)
    ctx.restore()
    ctx.stroke()
  }

  private def fillOval(ctx: GraphicsContext, x: Double, y: Double, w: Double, h: Double): Unit = {
    ctx.save()
    ctx.translate(x, y)
    ctx.scale(w / h, 1)
    ctx.beginPath()
    ctx.arc(h / 2, h / 2, h / 2, 0, 2 * math.Pi)
    ctx.restore()
    ctx.fill()
  }
}