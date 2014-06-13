package edu.depauw.scales.graphics

import Base._
  
case class Composite(over: Graphic, under: Graphic) extends Graphic {
  lazy val bounds: Bounds = over.bounds union under.bounds
  
  def render(ctx: GraphicsContext): Unit = {
    under.render(ctx)
    over.render(ctx)
  }
}