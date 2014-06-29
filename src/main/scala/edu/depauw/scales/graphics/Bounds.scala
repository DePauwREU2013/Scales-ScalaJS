package edu.depauw.scales.graphics

import Base._

// Describes the bounding box for the shape. The reference point is always at (0, 0).
sealed trait Bounds {
  def left: Double
  def right: Double
  def top: Double
  def bottom: Double
  
  def width: Double = right - left
  def height: Double = bottom - top
  
  def center: Double = (left + right) / 2
  def middle: Double = (top + bottom) / 2

  def union(that: Bounds): Bounds
  
  def transform(xform: AffineTransform): Bounds
}

case object EmptyBounds extends Bounds {
  val left: Double = 0
  val right: Double = 0
  val top: Double = 0
  val bottom: Double = 0

  def union(that: Bounds): Bounds = that
  
  def transform(xform: AffineTransform): Bounds = this
}

case class HorizontalStrut(left: Double, right: Double) extends Bounds {
  val top: Double = 0
  val bottom: Double = 0

  def union(that: Bounds): Bounds = that match {
    case EmptyBounds => this
    case HorizontalStrut(l, r) => HorizontalStrut(left min l, right max r)
    case VerticalStrut(t, b) => RectBounds(left, right, t, b)
    case RectBounds(l, r, t, b) => RectBounds(left min l, right max r, t, b)
  }
  
  def transform(xform: AffineTransform): Bounds =
    RectBounds(left, right, 0, 0).transform(xform)
}

case class VerticalStrut(top: Double, bottom: Double) extends Bounds {
  val left: Double = 0
  val right: Double = 0

  def union(that: Bounds): Bounds = that match {
    case EmptyBounds => this
    case HorizontalStrut(l, r) => RectBounds(l, r, top, bottom)
    case VerticalStrut(t, b) => VerticalStrut(top min t, bottom max b)
    case RectBounds(l, r, t, b) => RectBounds(l, r, top min t, bottom max b)
  }
  
  def transform(xform: AffineTransform): Bounds =
    RectBounds(0, 0, top, bottom).transform(xform)
}

case class RectBounds(left: Double, right: Double, top: Double, bottom: Double) extends Bounds {
  def union(that: Bounds): Bounds = that match {
    case EmptyBounds => this
    case HorizontalStrut(l, r) => RectBounds(left min l, right max r, top, bottom)
    case VerticalStrut(t, b) => RectBounds(left, right, top min t, bottom max b)
    case RectBounds(l, r, t, b) => RectBounds(left min l, right max r, top min t, bottom max b)
  }
  
  def transform(xform: AffineTransform): Bounds = {
    val (x1, y1) = xform((left, top))
    val (x2, y2) = xform((right, top))
    val (x3, y3) = xform((right, bottom))
    val (x4, y4) = xform((left, bottom))
    
    val l = x1 min x2 min x3 min x4
    val r = x1 max x2 max x3 max x4
    val t = y1 min y2 min y3 min y4
    val b = y1 max y2 max y3 max y4

    if (t == b) {
      HorizontalStrut(l, r)
    } else if (l == r) {
      VerticalStrut(t, b)
    } else {
      RectBounds(l, r, t, b)
    }
  }
}

// TODO add non-rectangular bounds? circles, perhaps?
// TODO generalize to "envelopes" (compute a boundary given an arbitrary vector) as in Diagrams?
