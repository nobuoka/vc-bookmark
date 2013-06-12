var bookmarksUri = "api/bookmarks";
var bookmarkUri = "api/bookmark";

var BookmarksView = function (elem) {
    this._list;
    this._elem = elem || document.createElement("div");
};
BookmarksView.prototype.setList = function (list) {
    var pe = this._elem;
    while (pe.lastChild) pe.removeChild(pe.lastChild);
    var that = this;
    list.forEach(function (b) {
        that._elem.appendChild(that.getView(b));
    });
};
BookmarksView.prototype.getView = function (b, oldElemOrNull) {
    var elem = oldElemOrNull || document.createElement("div");
    elem.textContent = b.url;
    return elem;
};

var bookmarksView = new BookmarksView(document.getElementById("bookmarks-container"));

function updateBookmarks() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", bookmarksUri, true);
    xhr.responseType = "json";
    xhr.addEventListener("load", function (evt) {
        var bookmarks = xhr.response;
        if (typeof bookmarks === "string") bookmarks = JSON.parse(bookmarks);
        bookmarksView.setList(bookmarks);
    });
    xhr.addEventListener("error", function (evt) {
        var resMsg = xhr.responseText;
        alert(resMsg);
    });
    xhr.send();
}

function sendAddingRequest(url) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", bookmarkUri, true);
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.addEventListener("load", function (evt) {
        updateBookmarks();
    });
    xhr.addEventListener("error", function (evt) {
        var resMsg = xhr.responseText;
        alert(resMsg);
    });
    var content = JSON.stringify({ url: url });
    console.log(content);
    xhr.send(content);
}

updateBookmarks();
document.getElementById("bookmark-adding-form").addEventListener("submit", function (evt) {
    var url = evt.currentTarget.elements["url"].value;
    sendAddingRequest(url);
    evt.preventDefault();
}, false);
