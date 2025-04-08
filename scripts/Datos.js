let provider= {
    "id": "909436f7-805d-4041-a0da-8319c6d0d8e1",
    "providerName": "JaityManga",
    "providedLang": "en",
    "logoUrl": "http://jaityManga.com/source",
    "isActive": true
}

let manga = {
    "id": "a6c84040-cfcd-4a6f-b005-643b96385281",
    "title": "Berserk",
    "author": "Kentaro Miura",
    "description": "Un héroe que derrota a todos de un solo golpe.",
    "chaptersCount": 0,
    "volumesCount": 0,
    "faviconUrl": "https://favicon.url/opm.png",
    "coverUrl": "https://cover.url/opm.jpg",
    "rating": null,
    "createdAt": "2025-04-06T01:12:21.992603093Z",
    "updatedAt": "2025-04-06T01:12:21.992605468Z",
    "volumes": null
}

let volume ={
    "id": "b323e6b4-b5a0-4200-9e34-45d29f969b87",
    "mangaId": "a6c84040-cfcd-4a6f-b005-643b96385281",
    "volumeNumber": 1,
    "title": "Volumen 1",
    "coverUrl": "https://cover.url/vol1.jpg",
    "createdAt": "2025-04-06T01:14:46.915647835Z",
    "updatedAt": "2025-04-06T01:14:46.915649791Z"
}

let chapter = {
    "id": "64fa9123-4b15-4640-afbf-470587d7bd57",
    "volumeId": "b323e6b4-b5a0-4200-9e34-45d29f969b87",
    "chapterNumber": "1",
    "title": "Capítulo 1",
    "createdAt": "2025-04-06T15:19:51.717675342Z",
    "updatedAt": "2025-04-06T15:19:51.717677298Z"
}

let chapterSource = {
    "id": "dca05595-3d7c-444d-9d3e-c85590d05495",
    "languageCode": "en",
    "providerName": "JaityManga",
    "logoUrl": "http://jaityManga.com/source",
    "isActive": true
}

let chapterSourcePage= {
    "id": "8d8dd3c8-4a33-4535-9985-cb2148780f28",
    "pageNumber": 1,
    "imageUrl": "https://manga.page/1.jpg",
    "createdAt": "2025-04-06T15:26:11.631821161Z",
    "updatedAt": "2025-04-06T15:26:11.631823465Z"
}


let getPages = [
    {
        "id": "8d8dd3c8-4a33-4535-9985-cb2148780f28",
        "pageNumber": 1,
        "imageUrl": "https://manga.page/1.jpg",
        "createdAt": "2025-04-06T15:26:11.631821Z",
        "updatedAt": "2025-04-06T15:26:11.631823Z"
    }
]

let likeCreated = {
    "timestamp": "2025-04-06T15:36:09.169858469Z",
    "status": 201,
    "message": "Like agregado correctamente.",
    "path": "/api/v1/me/likes/a6c84040-cfcd-4a6f-b005-643b96385281",
    "data": null
}

let clientContext = {
    "userId": "client@jaity.com",
    "preferences": null,
    "lastRead": null,
    "readingHistory": [
        {
            "mangaId": "a6c84040-cfcd-4a6f-b005-643b96385281",
            "chapterId": "64fa9123-4b15-4640-afbf-470587d7bd57",
            "lastPageRead": 1,
            "lastReadAt": "2025-04-06T15:34:30.595Z",
            "status": "IN_PROGRESS",
            "tags": null
        }
    ],
    "favorites": [
        "a6c84040-cfcd-4a6f-b005-643b96385281"
    ],
    "lastActiveAt": "2025-04-06T15:36:09.167Z"
}

let comment={
    "id": "67f2fc706d221f2d8742a665",
    "userId": "client@jaity.com",
    "mangaId": "a6c84040-cfcd-4a6f-b005-643b96385281",
    "chapterId": "64fa9123-4b15-4640-afbf-470587d7bd57",
    "content": "Buen capitulo bro",
    "createdAt": "2025-04-06T22:13:04.027155976Z"
}


let lastRead ={
    "mangaId": "a6c84040-cfcd-4a6f-b005-643b96385281",
    "chapterId": "64fa9123-4b15-4640-afbf-470587d7bd57",
    "lastPageRead": 1,
    "lastReadAt": "2025-04-06T23:37:09.914Z",
    "status": "IN_PROGRESS",
    "tags": null
}

let history = [
    {
        "mangaId": "a6c84040-cfcd-4a6f-b005-643b96385281",
        "chapterId": "64fa9123-4b15-4640-afbf-470587d7bd57",
        "lastPageRead": 1,
        "lastReadAt": "2025-04-06T23:37:09.914Z",
        "status": "IN_PROGRESS",
        "tags": null
    }
]