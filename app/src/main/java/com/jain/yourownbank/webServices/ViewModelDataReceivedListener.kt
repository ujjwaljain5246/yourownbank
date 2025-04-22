package com.jain.yourownbank.webServices

import com.jain.yourownbank.models.CommonModel

interface ViewModelDataReceivedListener {

    fun dataReceivedInRepositorySuccess(commonModel: CommonModel, requestCode: String)

    fun dataReceivedInRepositoryFailed(message: String?, requestCode: String, responseCode: Int)


}