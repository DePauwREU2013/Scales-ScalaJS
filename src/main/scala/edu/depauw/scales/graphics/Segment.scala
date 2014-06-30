package edu.depauw.scales.graphics

import Base._

trait Segment {
  def render(ctx: GraphicsContext): Unit
}

case class PointSegment(x: Double, y: Double) extends Segment {
  def render(ctx: GraphicsContext): Unit = ctx.moveTo(x, y)
}

case class LineSegment(x: Double, y: Double) extends Segment {
  def render(ctx: GraphicsContext): Unit = ctx.lineTo(x, y)
}

case class ArcSegment(x1: Double, y1: Double, x2: Double, y2: Double, radius: Double) extends Segment {
  def render(ctx: GraphicsContext): Unit = ctx.arcTo(x1, y1, x2, y2, radius)
}

case class QuadSegment(cpx: Double, cpy: Double, x: Double, y: Double) extends Segment {
  def render(ctx: GraphicsContext): Unit = ctx.quadraticCurveTo(cpx, cpy, x, y)
}

case class BezierSegment(cp1x: Double, cp1y: Double, cp2x: Double, cp2y: Double, x: Double, y: Double) extends Segment {
  def render(ctx: GraphicsContext): Unit = ctx.bezierCurveTo(cp1x, cp1y, cp2x, cp2y, x, y)
}

case object CloseSegment extends Segment {
  def render(ctx: GraphicsContext): Unit = ctx.closePath()
}