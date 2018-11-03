package yablonski.a.model

import yablonski.a.model.map.items.Creature
import yablonski.a.model.map.items.Food
import yablonski.a.model.map.items.MapCreature
import yablonski.a.model.map.items.SnakeCreature
import java.util.Random
import kotlin.collections.ArrayList
import kotlin.collections.HashSet


class FieldMap(private val mapWidth: Int, private val mapHeight: Int) {

    private val creatureSet = HashSet<MapCreature>()

    fun getBlocks(): List<Block> {
        return creatureSet.flatMap { creature -> creature.body }
    }

    private fun addCreature(mapCreature: MapCreature) {
        creatureSet.add(mapCreature)
    }

    fun spawnSnake(size: Int = 2): Creature {
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

    // проверка налиция опоры ниже mapCreature
    fun isPillarBellow(mapCreature: MapCreature): Boolean {
        mapCreature.body.forEach {
            // нижний край поля
            if (it.y == 0) {
                return true
            }
        }

        var mapCx: Int
        var mapCy: Int
        creatureSet.forEach {
            if (it != mapCreature) {
                // избегаем проверки самого себя
                it.body.forEach {
                    mapCx = it.x
                    mapCy = it.y
                    mapCreature.body.forEach {
                        // it.y - 1  - проверка координаты снизу
                        if (it.x == mapCx && it.y - 1 == mapCy) {
                            return true
                        }
                    }
                }
            }
        }
        return false
    }

    fun isNextStepAllow(snake: SnakeCreature): Boolean {
        val tempHead = Block(snake.head)
        tempHead.move(snake.movingDirection)
        if (tempHead.x <= 0 || tempHead.x > mapWidth) {
            // границы по горозонтали
            return false
        } else if (tempHead.y < 0 || tempHead.y > mapHeight) {
            // по вертикали
            return false
        }

        creatureSet.forEach {
            if (it != snake && it !is Food) {
                // избегаем проверки самого себя
                it.body.forEach {
                    if (it.x == tempHead.x && it.y == tempHead.y) {
                        return false
                    }
                }
            }
        }
        return true
    }


    fun checkLayers(snake: SnakeCreature, food: Food?) {
        fun collapseLayer(layerI: Int) {
            val toRemove = ArrayList<Block>()
            creatureSet.forEach { mapCreature ->
                if (mapCreature != snake && mapCreature != food) {
                    toRemove.clear()
                    mapCreature.body.forEach {
                        if (it.y > layerI) {
                            it.y--
                        } else if (it.y == layerI) {
                            toRemove.add(it)
                        }
                    }
                    mapCreature.body.removeAll(toRemove)
                }
            }
        }

        var fieldBlockCounter: Int
        for (yLayer in 0..mapHeight) {
            fieldBlockCounter = 0
            creatureSet.forEach { mapCreature ->
                if (mapCreature != snake && mapCreature != food) {
                    mapCreature.body.forEach {
                        if (it.y == yLayer) {
                            fieldBlockCounter++
                        }
                    }
                }
            }
            if (fieldBlockCounter == mapWidth) {
                // если вся строка занята
                collapseLayer(yLayer)
                checkLayers(snake, food)
            }
        }
    }
}