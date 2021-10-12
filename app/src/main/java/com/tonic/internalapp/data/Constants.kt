package com.tonic.internalapp.data

class Constants {
    class ACTION {
        companion object {
            const val ACTION_LOGIN_ACTION : String = "com.tonic.internal.LoginAction"
            const val ACTION_LOGIN_SUCCESS : String = "com.tonic.internal.LoginSuccess"
            const val ACTION_LOGIN_FAILED : String = "com.tonic.internal.LoginFailed"
            const val ACTION_LOGIN_NETWORK_ERROR : String = "com.tonic.internal.LoginNetworkError"
            //home
            const val ACTION_HOME_GO_TO_ANNOUNCEMENT_ACTION: String = "com.tonic.internal.HomeGoToAnnouncementAction"
            const val ACTION_HOME_GO_TO_EDIT_ACTION: String = "com.tonic.internal.HomeGoToEditAction"
            const val ACTION_HOME_GO_TO_BALANCE_ACTION: String = "com.tonic.internal.HomeGoToBalanceAction"
            //Announcement
            const val ACTION_ANNOUNCEMENT_UPDATE_ACTION: String = "com.tonic.internal.AnnouncementUpdateAction"
        }
    }
}