package edu.depauw.scales.graphics

import Base._

trait Path {
  def render(ctx: GraphicsContext): Unit
}

case class ClosedSimplePath(start: Point, segments: Segment*) extends Path {
  def render(ctx: GraphicsContext): Unit = {
    ctx.beginPath()
    ctx.moveTo(start._1, start._2)
    for (segment <- segments) segment.render(ctx)
    ctx.closePath()
    ctx.fill()
    ctx.stroke()
  }
}

case class OpenSimplePath(start: Point, segments: Segment*) extends Path {
  def render(ctx: GraphicsContext): Unit = {
    ctx.beginPath()
    ctx.moveTo(start._1, start._2)
    for (segment <- segments) segment.render(ctx)
    ctx.stroke()
  }
}

case class CompoundPath(paths: Path*) extends Path {
  def render(ctx: GraphicsContext): Unit = {
    for (path <- paths) path.render(ctx)
  }  
}

trait Segment {
  def render(ctx: GraphicsContext): Unit
}

case class LineSegment(x: Double, y: Double) extends Segment {
  def render(ctx: GraphicsContext): Unit = ctx.lineTo(x, y)
}

case class ArcSegment(x1: Double, y1: Double, x2: Double, y2: Double, radius: Double) extends Segment {
  def render(ctx: GraphicsContext): Unit = ctx.arcTo(x1, y1, x2, y2, radius)
}

//case class QuadSegment(x: Double, y: Double) extends Segment {
//  def render(ctx: GraphicsContext): Unit = ctx.quadraticCurveTo(cpx, cpy, x, y)
//}
//
//case class BezierSegment(x: Double, y: Double) extends Segment {
//  def render(ctx: GraphicsContext): Unit = ctx.bezierCurveTo(cp1x, cp1y, cp2x, cp2y, x, y)
//}
