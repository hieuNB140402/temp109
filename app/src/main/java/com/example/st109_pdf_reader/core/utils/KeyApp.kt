package com.example.st109_pdf_reader.core.utils

object KeyApp {
    const val DOWNLOAD_ALBUM = "TakePhoto"
    const val TEMP_IMAGE_FILTER = "Image Background"
    const val FOLDER_CREATE_PDF = "My PDF"


    const val ALL_FILE = "ALL"
    const val WORD = "WORD"
    const val EXCEL = "EXCEL"
    const val PPT = "PPT"
    const val PDF = "PDF"

    const val RENAME = "RENAME"
    const val SAVE_FILE = "SAVE_FILE"
    const val SEARCH_FILE = "SEARCH_FILE"

    object KeyIntent {
        const val INTENT_KEY = "INTENT_KEY"
        const val ADD_TIME_KEY = "ADD_TIME_KEY"
        const val CUSTOM_KEY = "CUSTOM_KEY"
        const val INTENT_KEY_LANG = "INTENT_KEY_LANG"
        const val FROM_SAVE = "from_suc"
        const val PATH_KEY = "PATH_KEY"
        const val STATUS_KEY = "STATUS_KEY"
        const val FROM_SETTINGS = "FROM_SETTINGS"
        const val CREATE_KEY = "CREATE_KEY"
        const val SCAN_KEY = "SCAN_KEY"
    }

    object KeySharePreference {
        const val FIRST_LANG = "first_lang"
        const val FIRST_LANG_KEY = "first_access_lang"

        const val FIRST_PERMISSION = "first_permission"
        const val FIRST_PERMISSION_KEY = "first_access_permission"

        const val KEY_LANGUAGE = "KEY_LANGUAGE"

        const val RATE = "rate"
        const val RATE_KEY = "rate_5_star"

        const val COUNT_BACK = "count_back"
        const val COUNT_BACK_KEY = "back_count"

        const val KEY_FIRST_SCAN = "is_first_scan"
    }

    object KeyPermission {
        const val IS_STORAGE = "IS_STORAGE"
        const val STORAGE_KEY = "STORAGE_KEY"
        const val STORAGE_KEY_IMAGE = "STORAGE_KEY_IMAGE"
        const val IS_NOTIFICATION = "IS_NOTIFICATION"
        const val NOTIFICATION_KEY = "NOTIFICATION_KEY"
        const val CAMERA_KEY = "CAMERA_KEY"
        const val RING_TIME_KEY = "RING_TIME_KEY"
        const val RINGTONE_KEY = "RINGTONE_KEY"
        const val VIBRATION_KEY = "VIBRATION_KEY"
        const val SOUND_KEY = "SOUND_KEY"
        const val FLASH_KEY = "FLASH_KEY"
    }

    object RequestCode {
        const val STORAGE_PERMISSION_CODE = 999
        const val STORAGE_PERMISSION_CODE_IMAGE = 999234
        const val NOTIFICATION_PERMISSION_CODE = 997
        const val PICK_IMAGE_REQUEST_CODE = 103
        const val CAMERA_PERMISSION_CODE = 131289
        const val PICK_AUDIO_REQUEST_CODE = 10341347
        const val CAMERA_REQUEST_CODE = 491782634
    }

    object KeyAssets {
        const val ASSET_MANAGER = "file:///android_asset"
        const val DATA_ASSET = "file:///android_asset/data/"
        const val RINGTONE_DEFAULT = "Apple.mp3"
        const val AVATAR_DEFAULT = "file:///android_asset/image/avatar_default.png"
        const val AVATAR_ASSET = "avatar"
        const val DATA = "data"
        const val MUSIC_ASSET = "music"

        const val FIRST_PNG = "1.png"
        const val FIRST_JPG = "1.jpg"
        const val FIRST_WEBP = "1.webp"
    }

    object ValueApp {
        const val VIEW = 0
        const val SUCCESSFUL = 1

        const val HOME = 0
        const val RECENT = 1
        const val BOOKMARK = 2
        const val SAVED = 3


    }

    object DomainAPI {
        const val BASE_URL = "https://lvtglobal.site"
        const val BASE_URL_PREVENTIVE = "https://lvtglobal.tech"
        const val SUB_DOMAIN = "/public/app/TungSahurFakeVideoCall"
        const val HTTP = "https://"

        const val AVATAR = "avatar"
        const val BACKGROUND = "bg"
        const val VIDEO = "video"
    }
}
