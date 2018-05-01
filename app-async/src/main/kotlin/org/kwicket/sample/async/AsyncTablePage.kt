package org.kwicket.sample.async

import org.apache.wicket.model.IModel
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.kwicket.agilecoders.wicket.core.markup.html.bootstrap.table.KTableBehavior
import org.kwicket.agilecoders.wicket.extensions.markup.html.bootstrap.table.KBootstrapDefaultDataTable
import org.kwicket.component.q
import org.kwicket.model.model
import org.kwicket.model.res
import org.kwicket.sample.shared.page.SampleBasePage
import org.kwicket.toParams
import org.kwicket.wicket.extensions.ajax.markup.html.repeater.data.table.AsyncTableDataProviderLoadBehavior
import org.kwicket.wicket.extensions.ajax.markup.html.repeater.data.table.KAsyncSortableDataProvider
import org.kwicket.wicket.extensions.markup.html.repeater.data.table.KLambdaColumn
import org.wicketstuff.annotation.mount.MountPath
import java.io.Serializable

private data class Person(val id: Int, val firstName: String, val lastName: String) : Serializable

enum class PersonSort {
    FirstName,
    LastName
}

@MountPath("table/async")
class AsyncTablePage(params: PageParameters) : SampleBasePage(params = params) {

    companion object {
        private val people = (1..100).map { Person(id = it, firstName = "First #$it", lastName = "Last #$it") }.toList()
        private const val countDelay = 1L
        private const val itemsDelay = 3L
        private const val parallelParamName = "p"
        fun makeParams(parallel: Boolean) = (parallelParamName to parallel).toParams()
    }

    init {
        statelessHint = false
        val parallel = params["p"].toBoolean(false)
        if (parallel) add(AsyncTableDataProviderLoadBehavior())

        q(
            KBootstrapDefaultDataTable(
                id = "table1",
                columns = listOf(
                    KLambdaColumn(
                        sort = PersonSort.FirstName,
                        displayModel = "First Name".res(),
                        function = { it.firstName }),
                    KLambdaColumn(
                        sort = PersonSort.FirstName,
                        displayModel = "Last Name".res(),
                        function = { it.lastName })
                ),
                rowsPerPage = 10,
                dataProvider = KAsyncSortableDataProvider<Person, PersonSort>(
                    count = {
                        println("Started getting count")
                        Thread.sleep(countDelay * 1000L)
                        println("Finished getting count")
                        people.size.toLong()
                    },
                    modeler = { it.model() },
                    items = { first, size ->
                        println("Started Getting items")
                        Thread.sleep(itemsDelay * 1000L)
                        println("Finished Getting items")
                        people.subList(first.toInt(), (first + size).toInt()).asSequence()
                    }),
                behaviors = listOf(KTableBehavior(striped = true, bordered = true, condensed = true, hover = true))
            )
        )
        q(
            KBootstrapDefaultDataTable(
                id = "table2",
                columns = listOf(
                    KLambdaColumn(
                        sort = PersonSort.FirstName,
                        displayModel = "First Name".res(),
                        function = { it.firstName }),
                    KLambdaColumn(
                        sort = PersonSort.FirstName,
                        displayModel = "Last Name".res(),
                        function = { it.lastName })
                ), rowsPerPage = 10,
                dataProvider = KAsyncSortableDataProvider<Person, PersonSort>(
                    count = {
                        println("Started getting count")
                        Thread.sleep(countDelay * 1000L)
                        println("Finished getting count")
                        people.size.toLong()
                    },
                    modeler = { it.model() },
                    items = { first, size ->
                        println("Started Getting items")
                        Thread.sleep(itemsDelay * 1000L)
                        println("Finished Getting items")
                        people.subList(first.toInt(), (first + size).toInt()).asSequence()
                    }),
                behaviors = listOf(KTableBehavior(striped = true, bordered = true, condensed = true, hover = true))
            )
        )
    }

    override val pageTitleModel: IModel<String>
        get() = "Async Table Page".res()

}