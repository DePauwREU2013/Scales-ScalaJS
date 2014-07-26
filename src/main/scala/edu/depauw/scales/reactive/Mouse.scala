package edu.depauw.scales.reactive

import scala.scalajs.js
import js.annotation.JSExport
import org.scalajs.dom
import rx._


/*
** Usage: MouseClick.subscribe()
** This returns a rx tuple, the (x,y) position of the mouse when clicked
*/
object MouseClick {
	val xy = Var((0, 0))
	val x = Rx { xy()._1 }
	val y = Rx { xy()._2 }
	val changes = Var(0)

	private def updateXY(newX: Int, newY: Int): Unit = {
		xy() = (newX, newY)
	}

	val boundingBox = Canvas.canvas.getBoundingClientRect()

	private def listen() = Rx {

		Canvas.canvas.onclick = {
			(e: dom.MouseEvent) => {
				updateXY((e.asInstanceOf[dom.Touch].pageX - boundingBox.left).toInt, (e.asInstanceOf[dom.Touch].pageY - boundingBox.top).toInt)
				changes() += 1
			}
		}
	}

	listen()

	def subscribe(): Rx[(Int, Int)] = xy
	def subscribeX(): Rx[Int] = x
	def subscribeY(): Rx[Int] = y
	def subscribeChanges(): Rx[Int] = changes
}

/*
** Usage: MouseMove.subscribe()
** This returns a rx tuple, the (x,y) position of the mouse when mouse moves
*/
object MousePosition {
	val xy = Var((0, 0))
	val x = Rx { xy()._1 }
	val y = Rx { xy()._2 }
	val changes = Var(0)

	private def updateXY(newX: Int, newY: Int): Unit = {
		xy() = (newX, newY)
	}

	val boundingBox = Canvas.canvas.getBoundingClientRect()

	private def listen() = Rx {

		Canvas.canvas.onmousemove = {
			(e: dom.MouseEvent) => {
				updateXY((e.asInstanceOf[dom.Touch].pageX - boundingBox.left).toInt, (e.asInstanceOf[dom.Touch].pageY - boundingBox.top).toInt)
				changes() += 1
			}
		}
	}

	listen()

	def subscribe(): Rx[(Int, Int)] = xy
	def subscribeX(): Rx[Int] = x
	def subscribeY(): Rx[Int] = y
	def subscribeChanges(): Rx[Int] = changes
}
