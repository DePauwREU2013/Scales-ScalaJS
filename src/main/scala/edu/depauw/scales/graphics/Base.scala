package edu.depauw.scales.graphics

import org.scalajs.dom

import scala.language.implicitConversions

object Base {
  type GraphicsContext = dom.CanvasRenderingContext2D
  
  // HTML5's Canvas is missing these...
  implicit def decorateContext(ctx: dom.CanvasRenderingContext2D) = new {
    def strokeOval(x: Double, y: Double, w: Double, h: Double): Unit = {
      ctx.save()
      ctx.translate(x, y)
      ctx.scale(w/h, 1)
      ctx.beginPath()
      ctx.arc(h/2, h/2, h/2, 0, 2*math.Pi)
      ctx.restore()
      ctx.stroke()
    }
    
    def fillOval(x: Double, y: Double, w: Double, h: Double): Unit = {
      ctx.save()
      ctx.translate(x, y)
      ctx.scale(w/h, 1)
      ctx.beginPath()
      ctx.arc(h/2, h/2, h/2, 0, 2*math.Pi)
      ctx.restore()
      ctx.fill()
    }
  }
  
  implicit val reflectiveCalls = scala.language.reflectiveCalls // enables ctx.xxxOval w/out warning

  type Point = (Double, Double)

  case class AffineTransform(m11: Double, m12: Double, m21: Double, m22: Double, dx: Double, dy: Double) {
    def apply(p: Point): Point = {
      val (x, y) = p
      (m11 * x + m21 * y + dx, m12 * x + m22 * y + dy)
    }

    def *(that: AffineTransform): AffineTransform =
      AffineTransform(
        m11 * that.m11 + m21 * that.m12,
        m12 * that.m11 + m22 * that.m12,
        m11 * that.m21 + m21 * that.m22,
        m12 * that.m21 + m22 * that.m22,
        m11 * that.dx + m21 * that.dy + dx,
        m12 * that.dx + m22 * that.dy + dy)
  }

  object AffineTransform {
    def getTranslateInstance(dx: Double, dy: Double): AffineTransform =
      AffineTransform(1, 0, 0, 1, dx, dy)

    def getRotateInstance(ang: Angle): AffineTransform =
      AffineTransform(Cos(ang), Sin(ang), -Sin(ang), Cos(ang), 0, 0)
    // TODO special cases for n * 90 deg?

    def getScaleInstance(sx: Double, sy: Double): AffineTransform =
      AffineTransform(sx, 0, 0, sy, 0, 0)
  }

  val Pi = math.Pi
  val Tau = 2 * Pi

  /**
   * Typesafe angle units. A function with an Angle parameter will not accept a Double.
   * Conversions are defined so that, for example, "180 deg" will produce the same Angle
   * as "Pi rad" or "0.5 rev". Value classes (AnyVal) mean this does no object-creation.
   */
  class Angle(val inRadians: Double) extends AnyVal {
    def inDegrees: Double = math.toDegrees(inRadians)

    def inRevolutions: Double = inRadians / Tau

    override def toString: String = inRadians + " rad"

    def +(a: Angle): Angle = new Angle(inRadians + a.inRadians)

    def -(a: Angle): Angle = new Angle(inRadians - a.inRadians)

    def *(d: Double): Angle = new Angle(inRadians * d)

    def /(d: Double): Angle = new Angle(inRadians / d)
  }

  implicit val postfixOps = language.postfixOps // enables us to write "90 deg" without warning

  implicit class AngleFactory(val d: Double) extends AnyVal {
    def deg = new Angle(math.toRadians(d))
    def rad = new Angle(d)
    def rev = new Angle(d * Tau)

    def *(a: Angle): Angle = new Angle(d * a.inRadians)
  }

  def Sin(ang: Angle) = math.sin(ang.inRadians)

  def Cos(ang: Angle) = math.cos(ang.inRadians)

  def Tan(ang: Angle) = math.tan(ang.inRadians)
}