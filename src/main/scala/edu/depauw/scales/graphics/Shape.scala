package edu.depauw.scales.graphics

import Base._

case class Shape(segments: List[Segment], bounds: Bounds, closed: Boolean = true) extends Graphic {
  def render(ctx: GraphicsContext): Unit = {
    ctx.beginPath()
    ctx.save()
    ctx.translate(bounds.left, bounds.top)
    ctx.scale(bounds.right - bounds.left, bounds.bottom - bounds.top)
    for (segment <- segments) segment.render(ctx)
    ctx.restore()
    if (closed) ctx.fill()
    ctx.stroke()
  }
}

object Shape {
  def apply(width: Double, height: Double)(pb: PathBuilder): Graphic =
    Shape(pb.segments, RectBounds(0, width, 0, height))
}

object Rectangle {
  val segments: List[Segment] = List(
    PointSegment(0, 0),
    LineSegment(1, 0),
    LineSegment(1, 1),
    LineSegment(0, 1),
    CloseSegment)

  def apply(width: Double, height: Double): Graphic =
    Shape(segments, RectBounds(-width / 2, width / 2, -height / 2, height / 2))
}

object Ellipse {
  val segments: List[Segment] = List(
    PointSegment(0.5, 0),
    ArcSegment(1, 0, 1, 0.5, 0.5),
    ArcSegment(1, 1, 0.5, 1, 0.5),
    ArcSegment(0, 1, 0, 0.5, 0.5),
    ArcSegment(0, 0, 0.5, 0, 0.5))

  def apply(width: Double, height: Double): Graphic =
    Shape(segments, RectBounds(-width / 2, width / 2, -height / 2, height / 2))
}

object RegPoly {
  def apply(radius: Double, n: Int, m: Int = 1): Graphic = {
    val segments = for (i <- 1 to n)
      yield LineSegment(0.5 + 0.5 * math.cos(Tau * m * i / n), 0.5 + 0.5 * math.sin(Tau * m * i / n))
    Shape(PointSegment(1, 0.5) :: segments.toList, RectBounds(-radius, radius, -radius, radius))
  }
}

object Polyline {
  def apply(points: Point*): Graphic =
    if (points.isEmpty) {
      Blank
    } else {
      val bounds = findBounds(points)
      def scale(p: Point): Point =
        ((p._1 - bounds.left) / bounds.width, (p._2 - bounds.top) / bounds.height)
      val segments = findSegments(points, scale)
      val (x, y) = scale(points.head)
      Shape(PointSegment(x, y) :: segments.toList, bounds, false)
    }

  def findBounds(points: Seq[Point]): Bounds = {
    val left = points.map(_._1).min
    val right = points.map(_._1).max
    val top = points.map(_._2).min
    val bottom = points.map(_._2).max
    RectBounds(left, right, top, bottom)
  }

  def findSegments(points: Seq[Point], scale: Point => Point): Seq[Segment] = {
    for (p <- points.tail) yield {
      val (x, y) = scale(p)
      LineSegment(x, y)
    }
  }
}

object Polygon {
  def apply(points: Point*): Graphic =
    if (points.isEmpty) {
      Blank
    } else {
      val bounds = Polyline.findBounds(points)
      def scale(p: Point): Point =
        ((p._1 - bounds.left) / bounds.width, (p._2 - bounds.top) / bounds.height)
      val segments = Polyline.findSegments(points, scale) :+ CloseSegment
      val (x, y) = scale(points.head)
      Shape(PointSegment(x, y) :: segments.toList, bounds)
    }
}
