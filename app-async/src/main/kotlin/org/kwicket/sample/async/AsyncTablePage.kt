package org.kwicket.sample.async

import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
import org.apache.wicket.Component
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider
import org.apache.wicket.model.IModel
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.kwicket.component.q
import org.kwicket.model.model
import org.kwicket.model.res
import org.kwicket.sample.shared.page.SampleBasePage
import org.kwicket.wicket.extensions.ajax.markup.html.repeater.data.table.KLambdaColumn
import org.kwicket.wicket.extensions.markup.html.repeater.data.table.KDataTable
import org.wicketstuff.annotation.mount.MountPath
import java.io.Serializable
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.util.concurrent.TimeUnit.SECONDS
import kotlin.coroutines.experimental.AbstractCoroutineContextElement
import kotlin.coroutines.experimental.CoroutineContext
import kotlin.coroutines.experimental.coroutineContext

class First(val v: Long) : AbstractCoroutineContextElement(Key) {
    companion object Key : CoroutineContext.Key<First>
}

class Size(val v: Long) : AbstractCoroutineContextElement(Key) {
    companion object Key : CoroutineContext.Key<Size>
}

data class Person(val firstName: String, val lastName: String) : Serializable

enum class PersonSort {
    FirstName,
    LastName
}

data class DataProviderInfo(val start: Long, val count: Long) : Serializable

interface AsyncDataProvider<T, S> : ISortableDataProvider<T, S> {
    fun loadAsync(first: Long, count: Long)
}

suspend fun abc(start: Long, size: Long): Sequence<Person> {
    delay(3, SECONDS)
    return emptySequence()
}

open class KAsyncSortableDataProvider<T, S>(
    private val count: suspend () -> Long,
    private val items: suspend () -> Sequence<T>,
    private val modeler: (T) -> IModel<T>,
    private val context: (() -> CoroutineContext) = { DefaultDispatcher }
) : SortableDataProvider<T, S>(), AsyncDataProvider<T, S> {

    @Transient
    private var _deferredCount: Deferred<Long>? = null

    @Transient
    private var _deferredItems: Deferred<Sequence<T>>? = null

    private val deferredCount: Deferred<Long>
        get() {
            initDeferredCount()
            return _deferredCount!!
        }

    private fun deferredItems(first: Long, size: Long): Deferred<Sequence<T>> {
        initDeferredItems(first = first, size = size)
        return _deferredItems!!
    }

    private fun initDeferredCount() {
        _deferredCount.let {
            if (it == null) _deferredCount = async(context()) { count() }
        }
    }

    private fun initDeferredItems(first: Long, size: Long) {
        _deferredItems.let {
            val c = context()
            val ctx = c + Size(size) + First(first)
            println("A. ${LocalDateTime.now()}")
            if (it == null) _deferredItems = async(ctx) {
                println("1. ${LocalDateTime.now()}")
                val x = items()
                println("2. ${LocalDateTime.now()}")
                x
            }
            println("B. ${LocalDateTime.now()}")
        }
    }

    /*
        private fun initDeferred() {
        _deferred.let {
            if (it == null) _deferred = async(context()) { block() }
        }
    }
     */

    override fun loadAsync(first: Long, size: Long) {
        initDeferredCount()
        initDeferredItems(first, size)
    }

    override fun iterator(first: Long, size: Long): Iterator<T> = runBlocking {
        deferredItems(first = first, size = size).await().iterator()
    }

    override fun size(): Long = runBlocking { deferredCount.await() }
    override fun model(value: T): IModel<T> = modeler(value)

    override fun detach() {
        _deferredCount = null
        _deferredItems = null
    }
}

class AsyncTableDataProviderLoadBehavior : Behavior() {

    override fun onConfigure(component: Component) {
        super.onConfigure(component)
        if (component.isVisibleInHierarchy) {
            if (component is DataTable<*, *>) {
                val dataProvider = component.dataProvider
                if (dataProvider is AsyncDataProvider<*, *>) {
                    dataProvider.loadAsync(component.itemsPerPage * component.currentPage, component.itemsPerPage)
                    println("After the behavior")
                }
            }
        }
    }

}

@MountPath("table/async")
class AsyncTablePage(params: PageParameters) : SampleBasePage(params = params) {

    companion object {
        const val delayAmt = 3L
        val delayedTime = suspend {
            delay(time = delayAmt, unit = SECONDS)
            now()
        }
    }

    private val table1: DataTable<*, *>
    private val table2: DataTable<*, *>

    override fun onConfigure() {
        listOf(table1, table2).forEach {table ->
            val dataProvider = table.dataProvider
            if (dataProvider is AsyncDataProvider<*, *>) {
                dataProvider.loadAsync(table.currentPage * table.itemsPerPage, table.itemsPerPage)
            }
        }
        super.onBeforeRender()
    }

    init {
        val people = listOf(Person(firstName = "Donald", lastName = "Duck"))
        val columns: List<IColumn<Person, PersonSort>> = listOf(
            KLambdaColumn(sort = PersonSort.FirstName, displayModel = "First Name".res(), function = { it.firstName }),
            KLambdaColumn(sort = PersonSort.FirstName, displayModel = "Last Name".res(), function = { it.lastName }),
            KLambdaColumn(sort = PersonSort.FirstName, displayModel = "Render Time".res(), function = { LocalDateTime.now() })
        )
        val parallel = params["p"].toBoolean(false)
        val createBehaviors = {
            if (parallel) arrayOf(AsyncTableDataProviderLoadBehavior()) else emptyArray()
        }

        val ff = suspend {
            println(">>>>>>>>>>>> Start: ${LocalDateTime.now()}")
            delay(3)
            println("<<<<<<<<<<<< End: ${LocalDateTime.now()}")
            people.asSequence()
        }

        table1 = q(
            KDataTable(
                id = "table1", columns = columns, rowsPerPage = 10,
                dataProvider = KAsyncSortableDataProvider<Person, PersonSort>(
                    count = suspend { /*runBlocking { delay(unit = SECONDS, time = 3) };*/ 20L },
                    modeler = { it.model() },
                    items = suspend { println("#1; ${coroutineContext[First]!!.v}, ${coroutineContext[Size]!!.v}"); delay(3, SECONDS);sequenceOf(Person("Andrew", "Geery")) }),
                behaviors = *createBehaviors()
            )
        )
        table2 = q(
            KDataTable(
                id = "table2", columns = columns, rowsPerPage = 10,
                dataProvider = KAsyncSortableDataProvider<Person, PersonSort>(
                    count = suspend { /*runBlocking { delay(unit = SECONDS, time = 3) };*/ 10L },
                    modeler = { it.model() },
                    items = suspend { println("#2; ${coroutineContext[First]!!.v}, ${coroutineContext[Size]!!.v}"); delay(3, SECONDS);sequenceOf(Person("Harry", "Potter")) }),
                behaviors = *createBehaviors()
            )
        )
    }

    override val pageTitleModel: IModel<String>
        get() = "Async Table Page".res()

}