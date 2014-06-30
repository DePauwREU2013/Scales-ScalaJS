package edu.depauw.scales.graphics

import Base._

class PathBuilder(x: Double, y: Double, heading: Angle, accum: List[Segment]) {
  def segments: List[Segment] = accum.reverse

  def moveTo(p: Point): PathBuilder = {
    val (x2, y2) = p
    new PathBuilder(x2, y2, 0 deg, PointSegment(x2, y2) :: accum)
  }

  def turnTo(h: Angle): PathBuilder = new PathBuilder(x, y, h, accum)

  def lineTo(p: Point): PathBuilder = {
    val (x2, y2) = p
    val h = Atan2(y2 - y, x2 - x)
    new PathBuilder(x2, y2, h, LineSegment(x2, y2) :: accum)
  }

  def curveTo(p: Point): PathBuilder = new PendingCurvePathBuilder(x, y, p._1, p._2, heading, accum)
  
  def moveBy(p: Point): PathBuilder = moveTo((x + p._1, y + p._2))
  def turnBy(a: Angle): PathBuilder = turnTo(heading + a)
  def lineBy(p: Point): PathBuilder = lineTo((x + p._1, y + p._2))
  def curveBy(p: Point): PathBuilder = curveTo((x + p._1, y + p._2))
  
  def forward(d: Double): PathBuilder = lineBy((d * Cos(heading), d * Sin(heading)))
  def back(d: Double): PathBuilder = forward(-d)
  def skip(d: Double): PathBuilder = moveBy((d * Cos(heading), d * Sin(heading)))
  def skipBack(d: Double): PathBuilder = skip(-d)
  def right(a: Angle): PathBuilder = turnBy(a)
  def left(a: Angle): PathBuilder = turnBy(-a)
}

class PendingCurvePathBuilder(x: Double, y: Double, x2: Double, y2: Double, heading: Angle, accum: List[Segment])
  extends PathBuilder(x, y, heading, accum) {
  private def complete(h: Angle): PathBuilder = {
    // Control point distance is one-third the distance (x, y) to (x2, y2) -- THIS IS ARBITRARY
    val dx = x2 - x
    val dy = y2 - y
    val dist = math.sqrt(dx * dx + dy * dy) / 3
    val cp1x = x + dist * Cos(heading)
    val cp1y = y + dist * Sin(heading)
    val cp2x = x2 - Cos(h) * dist
    val cp2y = y2 - Sin(h) * dist
    new PathBuilder(x2, y2, h, BezierSegment(cp1x, cp1y, cp2x, cp2y, x2, y2) :: accum)
  }
  
  override def segments: List[Segment] = complete(0 deg).segments
  
  override def moveTo(p: Point): PathBuilder = complete(0 deg).moveTo(p)
  
  override def turnTo(h: Angle): PathBuilder = complete(h).turnTo(h)
  
  override def lineTo(p: Point): PathBuilder = {
    val (x3, y3) = p
    val h = Atan2(y3 - y2, x3 - x2)
    complete(h).lineTo(p)
  }
  
  override def curveTo(p: Point): PathBuilder = complete(0 deg).curveTo(p)
}

object PathBuilder {
  def apply(p: Point): PathBuilder = {
    val (x, y) = p
    new PathBuilder(x, y, 0 deg, List(PointSegment(x, y)))
  }
}