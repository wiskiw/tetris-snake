package yablonski.a.model

import yablonski.a.model.map.items.Creature
import yablonski.a.model.map.items.Food
import yablonski.a.model.map.items.MapCreature
import yablonski.a.model.map.items.SnakeCreature
import java.util.Random
import kotlin.collections.ArrayList
import kotlin.collections.HashSet


class FieldMap(private val mapWidth: Int, private val mapHeight: Int) {

    private val staticBlocks = HashSet<Block>()

    private lateinit var snake: Creature
    private var food: Food? = null

    fun getBlocksToDraw(): List<Block> {
        val blocks = ArrayList<Block>()
        blocks.addAll(staticBlocks)
        blocks.addAll(snake.body)
        food?.let { blocks.addAll(it.body) }
        return blocks
    }

    fun spawnSnake(size: Int = 2) {
        staticBlocks.addAll(snake.body)

        val randX = Random().nextInt(mapWidth) + 1
        //mapWidth is the maximum and the 1 is minimum

        this.snake = Creature().apply {
            setHead(Block(randX, mapHeight - size, SnakeCreature.COLOR_HEAD), Direction.DOWN)
            for (i in size - 1 downTo 1) {
                addToBody(Block(randX, mapHeight - i, SnakeCreature.COLOR_BODY))
            }
        }
    }

    fun spawnFood() {
        fun getHigherPoint(): Int {
            var higherX = 1
            staticBlocks.forEach {
                if (it.x > higherX) {
                    higherX = it.x
                }
            }
            return higherX
        }

        val random = Random()
        val hp = getHigherPoint()
        val xP = random.nextInt(mapWidth) + 1
        val yP = random.nextInt(mapHeight - hp) + hp
        this.food = Food(xP, yP).apply {
            println("New Food: ${toString()}")
        }
    }

    // проверка налиция опоры ниже mapCreature
    fun isFlorBellow(mapCreature: MapCreature): Boolean {
        mapCreature.body.forEach {
            // нижний край поля
            if (it.y == 0) {
                return true
            }
        }

        staticBlocks.forEach { staticBlock ->
            mapCreature.body.forEach {
                // it.y - 1  - проверка координаты снизу
                if (it.x == staticBlock.x && it.y - 1 == staticBlock.y) {
                    return true
                }
            }
        }
        return false
    }

    fun isNextStepAllow(): Boolean {
        val tempHead = Block(snake.head)
        tempHead.move(snake.movingDirection)

        // проверка границ
        if (tempHead.x <= 0 || tempHead.x > mapWidth) {
            // границы по горозонтали
            return false
        } else if (tempHead.y < 0 || tempHead.y > mapHeight) {
            // по вертикали
            return false
        }

        // проверка столкновения со статичными блоками
        staticBlocks.forEach {
            if (it.x == tempHead.x && it.y == tempHead.y) {
                return false
            }
        }

        // проверка столкновения с хвостом
        snake.body.forEachIndexed { index, block ->
            if (index != snake.body.size - 1) {
                // пропускаем последний блок - хвост
                if (block.x == tempHead.x && block.y == tempHead.y) {
                    return false
                }
            }
        }
        return true
    }


    fun checkLayers() {
        fun collapseLayer(layerI: Int) {
            val toRemove = ArrayList<Block>()
            staticBlocks.forEach {
                if (it.y > layerI) {
                    it.y--
                } else if (it.y == layerI) {
                    toRemove.add(it)
                }
            }
            staticBlocks.removeAll(toRemove)
        }

        var fieldBlockCounter: Int
        for (yLayer in 0..mapHeight) {
            fieldBlockCounter = 0
            staticBlocks.forEach { block ->
                if (block.y == yLayer) {
                    fieldBlockCounter++
                }
            }
            if (fieldBlockCounter == mapWidth) {
                // если вся строка занята
                collapseLayer(yLayer)
                checkLayers()
            }
        }
    }
}