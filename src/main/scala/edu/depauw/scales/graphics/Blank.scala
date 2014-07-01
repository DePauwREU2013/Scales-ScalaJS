package edu.depauw.scales.graphics

import Base._

case object Blank extends Graphic {
  val bounds: Bounds = EmptyBounds
  
  def render(ctx: GraphicsContext): Unit = {}
}

object Space {
  def apply(width: Double, height: Double): Graphic =
    Bounded(Blank, RectBounds(0, width, 0, height))
}

object VSpace {
  def apply(height: Double): Graphic =
    Bounded(Blank, VerticalStrut(0, height))
}

object HSpace {
  def apply(width: Double): Graphic =
    Bounded(Blank, HorizontalStrut(0, width))
}
