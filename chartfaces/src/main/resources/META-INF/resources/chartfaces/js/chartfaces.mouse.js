// user-agent specific mouse utility.
// this is for all but google chrome and opera
var CFM = {};

CFM.MOUSEDOWN = "cf.mousedown";
CFM.MOUSEUP = "cf.mouseup";
CFM.MOUSEMOVE = "cf.mousemove";

CFM.mouseInit = function(chartId) {
	var svgElement = $('#'+chartId);
	var SVGRoot = svgElement.get(0);
	var clientRects = SVGRoot.getClientRects();
	var rect = clientRects[0];
	var clientLeft = rect.left;
	var clientTop = rect.top;
	var clientWidth = rect.right - rect.left;
	var clientHeight = rect.bottom - rect.top;
	var viewBox = SVGRoot.viewBox.baseVal;
	var viewLeft = viewBox.x;
	var viewTop = viewBox.y;
	var viewWidth = viewBox.width;
	var viewHeight = viewBox.height;
	var viewRight = viewLeft+viewWidth;
	var viewBottom = viewTop+viewHeight;

	var getRealX = function(evt) {
		return (evt.clientX-clientLeft)/clientWidth * viewWidth + viewLeft;
	}

	var getRealY = function(evt) {
		return (evt.clientY-clientTop)/clientHeight * viewHeight + viewTop;
	}

	var printed = false;
	var publishEvent = function(eventName, evt) {
		if(evt.witch && evt.witch>1 || evt.button && evt.button>1) {
			// right click we don't want to deal with
			return;
		}
		evt['realx'] = getRealX(evt);
		evt['realy'] = getRealY(evt);
		evt['viewHeight'] = viewHeight;
		evt['viewWidth'] = viewWidth;
		$().publish(eventName, [evt]);
	};

	svgElement.mousedown(function(evt) {
			console.log('publish: '+CFM.MOUSEDOWN);
		publishEvent(CFM.MOUSEDOWN, evt);
	});

	svgElement.mousemove(function(evt) {
		if (!printed) {
			console.log('publish: '+CFM.MOUSEMOVE);
			printed = true;
		}
		publishEvent(CFM.MOUSEMOVE, evt);
	});

	svgElement.mouseup(function(evt) {
		console.log('publish: '+CFM.MOUSEUP);
		publishEvent(CFM.MOUSEUP, evt);
	});
};