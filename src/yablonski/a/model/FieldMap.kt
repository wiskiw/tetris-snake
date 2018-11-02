package yablonski.a.model

import yablonski.a.model.map.items.Creature
import yablonski.a.model.map.items.Food
import yablonski.a.model.map.items.MapCreature
import yablonski.a.model.map.items.SnakeCreature
import java.util.Random
import kotlin.collections.HashSet


class FieldMap(private val mapWidth: Int, private val mapHeight: Int) {

    private val creatureSet = HashSet<MapCreature>()

    fun getBlocks(): List<Block> {
        return creatureSet.flatMap { creature -> creature.body }
    }

    fun addCreature(mapCreature: MapCreature) {
        creatureSet.add(mapCreature)
    }

    fun spawnSnake(size: Int = 2): SnakeCreature {
        val randX = Random().nextInt(mapWidth) + 1
        //mapWidth is the maximum and the 1 is minimum

        return Creature().apply {
            setHead(Block(randX, mapHeight - size, SnakeCreature.COLOR_HEAD), Direction.DOWN)
            for (i in size - 1 downTo 1) {
                addToBody(Block(randX, mapHeight - i, SnakeCreature.COLOR_BODY))
            }
            addCreature(this)
        }
    }

    fun spawnFood(): Food {
        fun getHigherPoint(): Int {
            var higherX = 1
            creatureSet.forEach {
                it.body.forEach {
                    if (it.x > higherX) {
                        higherX = it.x
                    }
                }
            }
            return higherX
        }

        val random = Random()
        val hp = getHigherPoint();
        val xP = random.nextInt(mapWidth) + 1
        val yP = random.nextInt(mapHeight - hp) + hp
        return Food(xP, yP).apply {
            addCreature(this)
            println("New Food: ${toString()}")
            return this
        }
    }

    fun remove(mapCreature: MapCreature) {
        creatureSet.remove(mapCreature)
    }

}