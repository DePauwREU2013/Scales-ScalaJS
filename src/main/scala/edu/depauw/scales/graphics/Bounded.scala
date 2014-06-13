package edu.depauw.scales.graphics

import Base._

case class Bounded(graphic: Graphic, bounds: Bounds) extends Graphic {
  def render(ctx: GraphicsContext): Unit = graphic.render(ctx)
}