package edu.depauw.scales.graphics

import Base._

case class Ellipse(width: Double, height: Double) extends Graphic {
  val bounds: Bounds = RectBounds(0, width, 0, height)

  def render(ctx: GraphicsContext): Unit = {
    ctx.fillOval(0, 0, width, height)
    ctx.strokeOval(0, 0, width, height)
  }
}