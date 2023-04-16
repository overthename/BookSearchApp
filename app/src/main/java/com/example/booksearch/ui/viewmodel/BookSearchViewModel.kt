package com.example.booksearch.ui.viewmodel

//@HiltViewModel
//class BookSearchViewModel @Inject constructor(
//    private val bookSearchRepository: BookSearchRepository,
//    private val workManager: WorkManager,
//    private val savedStateHandle: SavedStateHandle,
//) : ViewModel() {
//
//    //Api
//    private val _searchResult = MutableLiveData<SearchResponse>()
//    val searchResult: LiveData<SearchResponse> get() = _searchResult
//
//    fun searchBooks(query: String) = viewModelScope.launch(Dispatchers.IO) {
//        val response = bookSearchRepository.searchBooks(query, getSortMode(), 1, 15)
//        if (response.isSuccessful) {
//            response.body()?.let { body ->
//                _searchResult.postValue(body)
//            }
//        }
//    }
//
//    //Room
//    fun saveBook(book: Book) = viewModelScope.launch(Dispatchers.IO) {
//        bookSearchRepository.insertBooks(book)
//    }
//
//    fun deleteBook(book: Book) = viewModelScope.launch(Dispatchers.IO) {
//        bookSearchRepository.deleteBooks(book)
//    }
//
//    //val favoriteBooks: Flow<List<Book>> = bookSearchRepository.getFavoriteBooks()
//    val favoriteBooks: StateFlow<List<Book>> = bookSearchRepository.getFavoriteBooks()
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())
//
//
//    //savedState
//    var query = String()
//        set(value) {
//            field = value
//            savedStateHandle.set(SAVE_STATE_KEY, value)
//        }
//
//    init {
//        query = savedStateHandle.get<String>(SAVE_STATE_KEY) ?: ""
//    }
//
//    companion object {
//        private const val SAVE_STATE_KEY = "query"
//        private val WORKER_KEY = "cache_worker"
//    }
//
//    //DataStore
//    fun saveSortMode(value: String) = viewModelScope.launch(Dispatchers.IO) {
//        bookSearchRepository.saveSortMode(value)
//    }
//
//    suspend fun getSortMode() = withContext(Dispatchers.IO) {
//        bookSearchRepository.getSortMode().first()
//    }
//
//    fun savCacheDeleteMode(value: Boolean) = viewModelScope.launch(Dispatchers.IO) {
//        bookSearchRepository.saveCacheDeleteMode(value)
//    }
//
//    suspend fun getCacheDeleteMode() = withContext(Dispatchers.IO) {
//        bookSearchRepository.getCacheDeleteMode().first()
//    }
//
//    //Paging
//    val favoritePagingBooks: StateFlow<PagingData<Book>> =
//        bookSearchRepository.getFavoritePagingBooks()
//            .cachedIn(viewModelScope)
//            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())
//
//    private val _searchPagingResult = MutableStateFlow<PagingData<Book>>(PagingData.empty())
//
//    //ui에는 변경불가능한 걸 공개하도록함
//    val searchPagingResult: StateFlow<PagingData<Book>> = _searchPagingResult.asStateFlow()
//
//    fun searchBookPaging(query: String) {
//        viewModelScope.launch {
//            bookSearchRepository.searchBooksPaging(query, getSortMode())
//                .cachedIn(viewModelScope)
//                .collect {
//                    _searchPagingResult.value = it
//                }
//        }
//    }
//
//    //WorkManager
//    fun setWork() {
//        val constraints = Constraints.Builder()
//            .setRequiresCharging(true)
//            .setRequiresBatteryNotLow(true)
//            .build()
//
//        //15분 마다 한번 수행
//        val workRequest = PeriodicWorkRequestBuilder<CacheDeleteWorker>(15, TimeUnit.MINUTES)
//            .setConstraints(constraints)
//            .build()
//
//        workManager.enqueueUniquePeriodicWork(
//            WORKER_KEY, ExistingPeriodicWorkPolicy.REPLACE, workRequest
//        )
//    }
//
//    fun deleteWork() = workManager.cancelUniqueWork(WORKER_KEY)
//
//    fun getWorkStatus(): LiveData<MutableList<WorkInfo>> =
//        workManager.getWorkInfosForUniqueWorkLiveData(WORKER_KEY)
//
//}