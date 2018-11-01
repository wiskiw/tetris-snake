package yablonski.a.model

import java.awt.Color

class FieldMap(val sizeX: Int, val sizeY: Int) {


    private val map: Array<Array<Color?>> = Array(sizeY) {
        arrayOfNulls<Color>(sizeX)
    }


    fun forEach(callback: (Int, Int, Color?) -> Unit) {
        map.forEachIndexed { indexY, row ->
            row.forEachIndexed { indexX, color ->
                callback.invoke(indexX, indexY, color)
            }
        }
    }

    /*
    fun move(square: Square, direction: Direction) {
        square.move(direction)
    }
    */

}