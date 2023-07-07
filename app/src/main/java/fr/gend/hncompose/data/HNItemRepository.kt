//package fr.gend.hncompose.data
//
//import android.util.LruCache
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class HNItemRepository(val api : HackerNewsAPI) {
//
//    val cache = LruCache<Int, Item>(5_000)
//    fun fetchItem(id: Int): StateFlow<Item?> {
//
//        val cacheItem = cache[id]
//        if (cacheItem != null) {
//            return MutableStateFlow(cacheItem)
//        }
//
//        val sharedFlow = MutableStateFlow<Item?>(null)
//        api.item(id).enqueue(object : Callback<Item> {
//            override fun onResponse(call: Call<Item>, response: Response<Item>) {
//                val item = response.body()!!
//
//                cache.put(id, item)
//                sharedFlow.tryEmit(item)
//            }
//            override fun onFailure(call: Call<Item>, t: Throwable) {}
//        })
//        return sharedFlow
//    }
//}
