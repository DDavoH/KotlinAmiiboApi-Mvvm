package com.davoh.kotlinamiiboapi.ui.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.davoh.kotlinamiiboapi.database.model.Amiibo
import com.davoh.kotlinamiiboapi.database.model.AmiiboEntity
import com.davoh.kotlinamiiboapi.domain.Repository
import com.davoh.kotlinamiiboapi.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(private val repository: Repository): ViewModel(){

    private val amiibosData = MutableLiveData<String>()

    fun setAmiibo(amiiboName:String){
        amiibosData.value = amiiboName
    }

    //apenas inicie la aplicacion este valor se setea
    init {
        setAmiibo("")
    }


    val fetchAmiibosList = amiibosData.switchMap { amiiboName ->
        liveData<Resource<List<Amiibo>>>(Dispatchers.IO) {
            emit(Resource.Loading())
            try{
                if(amiiboName!=""){
                    emit(repository.getAmiibosList(amiiboName))
                }else{
                    emit(repository.getAllAmiibosList())
                }

            }catch (e:Exception){
                emit(Resource.Failure(e))
            }
        }
    }

    fun saveAmiibo(amiibo: AmiiboEntity){
        viewModelScope.launch {
            repository.insertAmiibo(amiibo)
        }
    }

    fun getFavoritesAmiibos() =
        liveData<Resource<List<Amiibo>>>( Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                emitSource(repository.getFavoritesAmiibos().map { Resource.Success(it) })
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }

    fun deleteAmiibo(amiibo: AmiiboEntity){
        viewModelScope.launch {
            repository.deleteAmiibo(amiibo)
        }
    }

    suspend fun isAmiiboFavorite(amiibo: Amiibo): Boolean =
        repository.isAmiiboFavorite(amiibo)

}