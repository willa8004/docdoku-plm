define([
	"require",
	"views/collapsible_list"
], function (
	require,
	CollapsibleListView
) {
	var FolderListView = CollapsibleListView.extend({
		itemViewFactory: function (model) {
			var FolderListItemView = require("views/folder_list_item"); // Circular dependency
			return new FolderListItemView({
				model: model
			});
		}
	});
	return FolderListView;
});
