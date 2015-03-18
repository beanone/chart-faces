// user-agent specific mouse utility.
// this is for google chrome and opera only
var CFM = {};

CFM.MOUSEDOWN = "cf.mousedown";
CFM.MOUSEUP = "cf.mouseup";
CFM.MOUSEMOVE = "cf.mousemove";

CFM.mouseInit = function(chartId) {
	var svgElement = $('#'+chartId);
	var SVGRoot = svgElement.get(0);
	var clientHeight = SVGRoot.clientHeight;
	var clientWidth = SVGRoot.clientWidth;
	var clientLeft = SVGRoot.clientLeft;
	var clientTop = SVGRoot.clientTop;
	var viewBox = SVGRoot.viewBox.baseVal;
	var viewLeft = viewBox.x;
	var viewTop = viewBox.y;
	var viewWidth = viewBox.width;
	var viewHeight = viewBox.height;
	var viewRight = viewLeft+viewWidth;
	var viewBottom = viewTop+viewHeight;

	var getRealX = function(evt) {
		return (evt.offsetX-clientLeft)/clientWidth * viewWidth + viewLeft;
	}

	var getRealY = function(evt) {
		return (evt.offsetY-clientTop)/clientHeight * viewHeight + viewTop;
	}

	var publishEvent = function(eventName, evt) {
		if (e.witch && e.witch > 1 || e.button && e.button > 1) {
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
		publishEvent(CFM.MOUSEDOWN, evt);
	});

	svgElement.mousemove(function(evt) {
		publishEvent(CFM.MOUSEMOVE, evt);
	});

	svgElement.mouseup(function(evt) {
		publishEvent(CFM.MOUSEUP, evt);
	});
};