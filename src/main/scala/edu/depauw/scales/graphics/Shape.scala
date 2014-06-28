package edu.depauw.scales.graphics

import Base._

case class Shape(path: Path, bounds: Bounds) extends Graphic {
  def render(ctx: GraphicsContext): Unit = {
    ctx.save()
    ctx.translate(bounds.left, bounds.top)
    ctx.scale(bounds.right - bounds.left, bounds.bottom - bounds.top)
    ctx.lineWidth = ctx.lineWidth / (bounds.right - bounds.left) // TODO this is a hack, but we can't do different x & y widths...
    path.render(ctx)
    ctx.restore()
  }
}

// TODO until the day when the lineWidth problem is fixed, use the old specialized versions of these?
//case class Rectangle(width: Double, height: Double) extends Graphic {
//  val bounds: Bounds = RectBounds(-width/2, width/2, -height/2, height/2)
//
//  def render(ctx: GraphicsContext): Unit = {
//    ctx.fillRect(-width/2, -height/2, width, height)
//    ctx.strokeRect(-width/2, -height/2, width, height)
//  }
//}
//case class Ellipse(width: Double, height: Double) extends Graphic {
//  val bounds: Bounds = RectBounds(-width/2, width/2, -height/2, height/2)
//
//  def render(ctx: GraphicsContext): Unit = {
//    ctx.fillOval(-width/2, -height/2, width, height)
//    ctx.strokeOval(-width/2, -height/2, width, height)
//  }
//}
object Rectangle {
  val path: Path = ClosedSimplePath((0, 0),
      LineSegment(1, 0),
      LineSegment(1, 1),
      LineSegment(0, 1))
  
  def apply(width: Double, height: Double): Graphic =
    Shape(path, RectBounds(-width/2, width/2, -height/2, height/2))
}

object Ellipse {
  val path: Path = ClosedSimplePath((0.5, 0),
      ArcSegment(1, 0, 1, 0.5, 0.5),
      ArcSegment(1, 1, 0.5, 1, 0.5),
      ArcSegment(0, 1, 0, 0.5, 0.5),
      ArcSegment(0, 0, 0.5, 0, 0.5))
  
  def apply(width: Double, height: Double): Graphic =
    Shape(path, RectBounds(-width/2, width/2, -height/2, height/2))
}

// TODO regular polygons, general polygons & polylines, paths as in Scales 3.0