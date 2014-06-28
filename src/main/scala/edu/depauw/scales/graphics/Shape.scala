package edu.depauw.scales.graphics

import Base._

case class Shape(path: Path, bounds: Bounds) extends Graphic {
  def render(ctx: GraphicsContext): Unit = {
    ctx.beginPath()
    ctx.save()
    ctx.translate(bounds.left, bounds.top)
    ctx.scale(bounds.right - bounds.left, bounds.bottom - bounds.top)
    path.render(ctx)
    ctx.restore()
    ctx.fill()
    ctx.stroke()
  }
}

object Rectangle {
  val path: Path = SimplePath((0, 0),
      LineSegment(1, 0),
      LineSegment(1, 1),
      LineSegment(0, 1),
      CloseSegment)
  
  def apply(width: Double, height: Double): Graphic =
    Shape(path, RectBounds(-width/2, width/2, -height/2, height/2))
}

object Ellipse {
  val path: Path = SimplePath((0.5, 0),
      ArcSegment(1, 0, 1, 0.5, 0.5),
      ArcSegment(1, 1, 0.5, 1, 0.5),
      ArcSegment(0, 1, 0, 0.5, 0.5),
      ArcSegment(0, 0, 0.5, 0, 0.5))
  
  def apply(width: Double, height: Double): Graphic =
    Shape(path, RectBounds(-width/2, width/2, -height/2, height/2))
}

// TODO regular polygons, general polygons & polylines, paths as in Scales 3.0