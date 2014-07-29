package edu.depauw.scales.music

import scala.scalajs.js
import js.annotation.JSExport
import org.scalajs.dom
import rx._

import edu.depauw.scales.act._

object Audio {
	val audioContext = js.Dynamic.newInstance(js.Dynamic.global.AudioContext)()
}

trait ScalesNote extends Scales {
	val ctx = Audio.audioContext

	//def play(t: Double): Unit

	def duration: Double

	def count: Int

	def before(that: ScalesNote): SoundComposite = SoundComposite(this, that)

	def after(that: ScalesNote): SoundComposite = SoundComposite(that, this)

	def par(that: ScalesNote): ParallelComposite = ParallelComposite(this, that)

	def act(time: Double = 0): Unit

	def transformAct(scale: Double): ScalesNote
}

/*
** @params vol: between 0 - 1
*/
case class Note(freq: Double, dur: Double = 1, vol: Double = 1) extends ScalesNote {

	def count = 1

	def duration = dur

	//def act(time: Double = 0) = play(time)

	def act(time: Double = 0): Unit = {
		val o = ctx.createOscillator()
		val g = ctx.createGain()

		o.frequency.value = freq
		g.gain.value = vol

		o.connect(g)
		g.connect(ctx.destination)

		o.start(ctx.currentTime.toString().toDouble + time)
		o.stop(ctx.currentTime.toString().toDouble + time + dur)
	}

	def transformAct(scale: Double): ScalesNote = {
		Note(freq, dur * scale, vol)
	}

	/*
	** returns a new note moved up @params octaves
	*/
	def >(octaves: Int = 1): Note = {
		val mult = math.pow(2, octaves)
		Note(freq * mult, dur, vol)
	}

	def <(octaves: Int = 1): Note = {
		val mult = math.pow(2, octaves)
		Note(freq / mult, dur, vol)
	}

	/*
	** returns a new note moved up 4 octaves
	*/
	def >>>> : Note = {
		Note(freq * 16, dur, vol)
	}

	def <<<< : Note = {
		Note(freq / 16, dur, vol)
	}

	def setVolume(newVol: Double): Note = {
		Note(freq, dur, newVol)
	}

	def setDuration(newDur: Double): Note = {
		Note(freq, newDur, vol)
	}
}

case class SoundComposite(first: ScalesNote, second: ScalesNote) extends ScalesNote {

	def count = first.count + second.count

	def duration = first.duration + second.duration

	def act(time: Double = 0): Unit = {
		first.act(time)
		second.act(time + first.duration)
	}

	def transformAct(scale: Double): ScalesNote = {
		SoundComposite(first.transformAct(scale), second.transformAct(scale))
	}

}

case class ParallelComposite(first: ScalesNote, second: ScalesNote) extends ScalesNote {

	def count = first.count + second.count

	def duration = math.max(first.duration, second.duration)

	def act(time: Double = 0): Unit = {
		first.act(time)
		second.act(time)
	}

	def transformAct(scale: Double): ScalesNote = {
		ParallelComposite(first.transformAct(scale), second.transformAct(scale))
	}
}

/*
** Default notes start at octave 3
** s signifies sharp
*/
object Silent extends Note(0, 1, 0)
object C extends Note(130.81)
object Cs extends Note(138.59)
object D extends Note(146.83)
object Ds extends Note(155.56)
object E extends Note(164.81)
object F extends Note(174.61)
object Fs extends Note(185)
object G extends Note(196)
object Gs extends Note(207.65)
object A extends Note(220)
object As extends Note(233.08)
object B extends Note(246.94)