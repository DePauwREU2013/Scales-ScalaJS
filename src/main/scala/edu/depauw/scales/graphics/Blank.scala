package edu.depauw.scales.graphics

import Base._

case object Blank extends Graphic {
  val bounds: Bounds = EmptyBounds
  
  def render(ctx: GraphicsContext): Unit = {}
}