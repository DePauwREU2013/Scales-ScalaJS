package edu.depauw.scales.graphics

import org.scalajs.dom

import scala.language.implicitConversions

object Base {
  type GraphicsContext = dom.CanvasRenderingContext2D
  
//  // HTML5's Canvas is missing these...
//  implicit def decorateContext(ctx: dom.CanvasRenderingContext2D) = new {
//    def strokeOval(x: Double, y: Double, w: Double, h: Double): Unit = {
//      ctx.save()
//      ctx.translate(x, y)
//      ctx.scale(w/h, 1)
//      ctx.beginPath()
//      ctx.arc(h/2, h/2, h/2, 0, Tau)
//      ctx.restore()
//      ctx.stroke()
//    }
//    
//    def fillOval(x: Double, y: Double, w: Double, h: Double): Unit = {
//      ctx.save()
//      ctx.translate(x, y)
//      ctx.scale(w/h, 1)
//      ctx.beginPath()
//      ctx.arc(h/2, h/2, h/2, 0, Tau)
//      ctx.restore()
//      ctx.fill()
//    }
//  }
//  
//  implicit val reflectiveCalls = scala.language.reflectiveCalls // enables ctx.xxxOval w/out warning

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
  class Angle(val inDegrees: Double) extends AnyVal {
    def inRadians: Double = math.toRadians(inDegrees)

    def inRevolutions: Double = inDegrees / 360

    override def toString: String = inDegrees + " deg"

    def +(a: Angle): Angle = new Angle(inDegrees + a.inDegrees)

    def -(a: Angle): Angle = new Angle(inDegrees - a.inDegrees)

    def *(d: Double): Angle = new Angle(inDegrees * d)

    def /(d: Double): Angle = new Angle(inDegrees / d)
  }

  implicit val postfixOps = language.postfixOps // enables us to write "90 deg" without warning

  implicit class AngleFactory(val d: Double) extends AnyVal {
    def deg = new Angle(d)
    def rad = new Angle(math.toDegrees(d))
    def rev = new Angle(d * 360)

    def *(a: Angle): Angle = new Angle(d * a.inDegrees)
  }

  def Sin(ang: Angle) = math.sin(ang.inRadians)

  def Cos(ang: Angle) = math.cos(ang.inRadians)

  def Tan(ang: Angle) = math.tan(ang.inRadians)
  
  sealed trait Color {
    def red: Int
    def green: Int
    def blue: Int
    def hue: Angle
    def saturation: Double
    def lightness: Double
    def alpha: Double
  }
  
  case class RGBA(red: Int, green: Int, blue: Int, alpha: Double) extends Color {
    override def toString: String = {
      if (alpha < 1.0) {
        s"rgba($red, $green, $blue, $alpha)"
      } else {
        s"rgb($red, $green, $blue)"
      }
    }
    
    // Source: http://en.wikipedia.org/wiki/HSL_and_HSV
    def hue: Angle = (60 deg) * (
      if (chroma == 0) 0
      else if (M == green) 2 + (blue - red) / chroma.toDouble
      else if (M == blue) 4 + (red - green) / chroma.toDouble
      else if (green < blue) 6 + (green - blue) / chroma.toDouble
      else (green - blue) / chroma.toDouble
    )
    
    def saturation: Double =
      if (chroma == 0) 0
      else chroma / 255.0 / (1 - math.abs(2 * lightness - 1))
      
    def lightness: Double = (M + m) / 510.0
    
    private def M: Int = red max green max blue
    private def m: Int = red min green min blue
    private def chroma: Int = M - m
  }
  
  // TODO normalize hue to 0-360?
  case class HSLA(hue: Angle, saturation: Double, lightness: Double, alpha: Double) extends Color {
    override def toString: String = {
      if (alpha < 1.0) {
        s"hsla(${hue.inDegrees}, ${100 * saturation}%, ${100 * lightness}%, $alpha)"
      } else {
        s"hsl(${hue.inDegrees}, ${100 * saturation}%, ${100 * lightness}%)"
      }
    }
    
    // Source: http://en.wikipedia.org/wiki/HSL_and_HSV
    def red: Int = math.round(
      if (H < 1 || H >= 5) C + m
      else if (H < 2 || H >= 4) X + m
      else m
    )
    
    def green: Int = math.round(
      if (H >= 1 && H < 3) C + m
      else if (H < 4) X + m
      else m
    )
    
    def blue: Int = math.round(
      if (H >= 3 && H < 5) C + m
      else if (H >= 2) X + m
      else m
    )
    
    private def C: Float = 255 * (1 - math.abs(2 * lightness.toFloat - 1)) * saturation.toFloat
    private def H: Float = hue.inDegrees.toFloat / 60
    private def X: Float = C * (1 - math.abs(H % 2 - 1))
    private def m: Float = 255 * lightness.toFloat - C / 2
  }
  
  object RGB {
    def apply(red: Int, green: Int, blue: Int): Color =
      RGBA(red, green, blue, 1.0)
  }
  
  object HSL {
    def apply(hue: Angle, saturation: Double, lightness: Double): Color =
      HSLA(hue, saturation, lightness, 1.0)
  }
  
  object Color {
    val red = RGB(255, 0, 0)
    val yellow = RGB(255, 255, 0)
    val green = RGB(0, 255, 0)
    val cyan = RGB(0, 255, 255)
    val blue = RGB(0, 0, 255)
    val magenta = RGB(255, 0, 255)
    val black = RGB(0, 0, 0)
    val white = RGB(255, 255, 255)
    val clear = RGBA(0, 0, 0, 0)
  }
  
  sealed trait Style {
    def apply(ctx: GraphicsContext): Unit
  }
  
  case class FillColor(c: Color) extends Style {
    def apply(ctx: GraphicsContext): Unit = {
      ctx.fillStyle = c.toString
    }
  }
  
  case class StrokeColor(c: Color) extends Style {
    def apply(ctx: GraphicsContext): Unit = {
      ctx.strokeStyle = c.toString
    }
  }
  
  case class StrokeWidth(w: Double) extends Style {
    def apply(ctx: GraphicsContext): Unit = {
      ctx.lineWidth = w
    }
  }
}