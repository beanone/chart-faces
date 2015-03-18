// declare our JS namespace
var CF ={};

// constants
CF.STATUS = "status";
CF.ERROR = "error";
CF.STOP = "stop";
CF.RESTART = "restart";
CF.BEFORE = "before";
CF.AFTER = "after";
CF.RESET = 'resetChart';
CF.ZOOM = 'zoom';
CF.WHEEL = 'wheel';
CF.UPDATED = 'updated';
CF.ERRORED = 'errored';
CF.MOVE = 'move';
CF.LOADIMAGE = 'loadImage';
CF.MORE = 'more';
CF.DONE = 'done';
CF.PROGRESS = 'progress';

// other members
CF._chartUpdateCount=0;

/**
 * Initializes all the event binding stuffs for the renderkit.
 */
CF.cfInit = function(chartId, interval, progressive) {
	CF.statusInit(CF.STATUS, null);
	CF.statusInit(CF.ERROR, CF.STATUS);
	CF.resetInit(chartId, progressive);
	CF.dragZoomInit(chartId, progressive);
	CF.imageLoadInit(chartId);
	if ( interval && interval != '' ) {
		CF.liveInit(chartId, interval);
	}
	if ( progressive && progressive != '' ) {
		CF.progressiveInit(chartId);
	}
	jsf.ajax.addOnError(function(data) {
		$().publish(CF.ERRORED);
		CF.ajaxError(data,chartId);
	});

	// insert title children tags to tags that has title attribute to support tooltip
	// We do this this way because no other tooltip methods can work reliably in all browsers
	$('#'+chartId+' *[title]').each(function(index) {
		var titleElem = document.createElementNS(svgNS, "svg:title");
		$(titleElem).text(this.getAttribute("title"));
		this.appendChild(titleElem);
	});
};

/**
 * Updates the chart with new JSAN data. The JSAN data complies with the JSAN
 * specification (NOTE: not JSON, but an extension of JSON). In addition, the
 * IDs of all top level nodes must be named as:
 * {tagName}.{parentId}{.blah.blah.that.makes.this.unique.id.in.the.JSON.object}.
 * tagName is the tag name of the element that you want to create.
 * parentId is the id of the parent node that the new element(s) will be created
 * in.
 * The content of the node matching the parentId will be emptied before the first
 * new element is filled in. Thus, it is important that elements identified by
 * the parentIds don't nest within each other.
 *
 * NOTE: This method is called form onload event, thus, don't call
 *
 * @param {Object} ds a JASON object that contains all the data for updating the DOM.
 */
CF.updateChart = function (ds) {
	var type = ds['payloadType'];
	var cancel = ds['cancel'];
	var payload = ds['payload'];
	var append = ds['append']; // are we updating existing nodes or creating new nodes.
	var progress = ds['progress'];
	if(progress>=0 && progress<100) {
		$().publish(CF.MORE);
	}
	if(progress>100) {
		$().publish(CF.DONE);
	}

	if(type && !cancel && (typeof progress == "undefined" || (progress>0 && progress<=100))) {
		// avoid updating if the JSon object is empty.
		if (type == 'image') {
			$().publish(CF.LOADIMAGE, [payload]);
		} else {
			var cleanedMap = {};
			for (var k in payload) {
				var jsan = new Jsan(payload[k]);
				var ids = k.split('.');
				var parentId = ids[1];
				if ( append ) {
					// do nothing
				} else if (!cleanedMap[parentId] ) {
					// makes sure that we only clear once
					cleanedMap[parentId]=true;
					$('#'+parentId).empty();
				}
				var parent = document.getElementById(parentId);
				for (var i = 0; i < jsan.size(); i++) {
					insertToDocument('svg', ids[0], jsan.get(i), parent);
				}
			}
		}
	}
};

CF.pollUpdate = function(data,sourceid) {
	// do nothing for now
	if ( data.status=='complete' && data.source.id==sourceid) {
		$().publish(CF.UPDATED);
		CF.showStatus('Chart updated. Count='+(++CF._chartUpdateCount));
	}
};

CF.imageLoadInit = function(chartId) {
	var xlinkNS="http://www.w3.org/1999/xlink";
	var imgElement = $('#'+chartId+'image');
	var svgImage = imgElement.get(0);
	imgElement.subscribe(
		CF.LOADIMAGE,
		function(url) {
			svgImage.setAttributeNS(xlinkNS,"xlink:href",url);
		});
};

CF.ajaxError = function(data, sourceid){
	if (data.source.id == sourceid) {
		$().publish(CF.STOP, ['Chart updates stopped due to error: ' + data.description]);
	}
};

CF.showStatus = function(statusMessage) {
	var messages = statusMessage;
	if ( statusMessage instanceof Array ) {
		messages = statusMessage;
	} else {
		messages = [statusMessage];
	}
	$().publish(CF.STATUS, messages);
};

CF.showError = function(errorMessage) {
	var messages = errorMessage;
	if ( errorMessage instanceof Array ) {
		messages = errorMessage;
	} else {
		messages = [errorMessage];
	}
	$().publish(CF.ERROR, messages);
}

CF.statusInit = function(name, fallBach) {
	var statusElement = $('*[name='+name+']');
	var statusText, now, timestamp;
	if ( statusElement ) {
		statusElement.subscribe(name, function(message) {
			statusText = statusElement.text();
			now = new Date();
			timestamp = now.getHours() + ':' + now.getMinutes() + ':' + now.getSeconds();
			statusText = timestamp + ' ' + message + '\r\n' + statusText;
			statusElement.text(statusText);
		});
	} else if (fallBack) {
		CF.statusInit(fallBack);
	}
};

CF.resetInit = function(chartId, progressive) {
	var svgElement = $('#'+chartId);
	svgElement.subscribe(
		CF.RESET,
		function() {
			try {
				jsf.ajax.request(
					chartId,
					null,
						{
							execute:chartId,
							onevent: function(data) {
								CF.pollUpdate(data,chartId);
							},
							"event.type":"reset"
						});
				CF.registerForProgressive(chartId, progressive);
			} catch (e) {
				CF.showError(e.toString());
			}
		},
		null);
	var resetButton = $('button[value=reset]');
	if ( resetButton ) {
		resetButton.click(function() {
			$().publish(CF.RESET);
		});
	}
};

CF.liveInit = function(chartId, interval) {
	var svgElement = $('#'+chartId);
	var token;
	var poll = function() {
		try {
			jsf.ajax.request(
				chartId,
				null,
					{
						execute:chartId,
					 	onevent: function(data) {
							CF.pollUpdate(data,chartId);
						},
						"event.type":"live"
					});
		} catch (e) {
			$().publish(CF.STOP, [e.toString()]);
		}
	};
	token = window.setInterval(poll,interval);
	svgElement.subscribe(CF.STOP, function(message) {
		if ( token ) {
			$().publish(CF.BEFORE, [CF.STOP]);
			window.clearInterval(token);
			token = null;
			if ( message ) {
				CF.showStatus(message);
			} else {
				CF.showStatus('Live chart polling stopped on request.');
			}
			$().publish(CF.AFTER, [CF.STOP]);
		}
	});

	svgElement.subscribe(CF.RESTART, function() {
		if ( token ) {
			$().publish(CF.STOP);
		}
		$().publish(CF.BEFORE, [CF.RESTART]);
		token = window.setInterval(poll,interval);
		CF.showStatus('Live chart polling restarted on request.');
		$().publish(CF.AFTER, [CF.RESTART]);
	});

	var stopButton = $('button[value='+CF.STOP+']');
	if ( stopButton ) {
		stopButton.click(function() {
			$().publish(CF.STOP);
		});
	}
	var restartButton = $('button[value='+CF.RESTART+']');
	if ( restartButton ) {
		restartButton.click(function() {
			$().publish(CF.RESTART);
		});
	}
};

CF.progressiveInit = function(chartId) {
	var svgElement = $('#'+chartId);
	var token;
	var index = -1;
	var poll = function() {
		index++;
		try {
			jsf.ajax.request(
				chartId,
				null,
					{
						execute:chartId,
					 	onevent: function(data) {
							CF.pollUpdate(data,chartId);
						},
						"index": index,
						"event.type":"progressive"
					});
		} catch (e) {
			$().publish(CF.ERROR, e.toString());
		}
	};

	svgElement.subscribe(CF.MORE, function() {
		poll();
	});

	svgElement.subscribe(CF.DONE, function() {
		svgElement.unsubscribe(CF.MORE);
	});

	poll();
};

CF.registerForProgressive = function(chartId, progressive) {
	var rendered = false;
	if(progressive && progressive!='') {
		var svgElement = $('#'+chartId);
		svgElement.subscribe(CF.MORE, function() {
			if(!rendered) {
				rendered = true;
				CF.progressiveInit(chartId, progressive);
			}
		});

		svgElement.subscribe(CF.DONE, function() {
			svgElement.unsubscribe(CF.MORE);
		});
	}
}

CF.dragZoomInit = function(chartId, progressive) {
	var svgElement = $('#'+chartId);
	var SVGRoot = svgElement.get(0);
	var clientHeight = SVGRoot.clientHeight;
	var clientWidth = SVGRoot.clientWidth;
	var clientLeft = SVGRoot.offsetLeft;
	var clientTop = SVGRoot.offsetTop;
	var downx, downy, upx, upy;
	var startTime, upTime;
	var mouseMoved = false, mouseDown = false;
	var viewBox = SVGRoot.viewBox.baseVal;
	var viewLeft = viewBox.x;
	var viewTop = viewBox.y;
	var viewWidth = viewBox.width;
	var viewHeight = viewBox.height;
	var viewRight = viewLeft+viewWidth;
	var viewBottom = viewTop+viewHeight;
	var mouseTracker = new MouseTracker();

	svgElement.subscribe(CF.MOVE, function(direction, dx, dy) {
		try {
			jsf.ajax.request(chartId, null, {
				"execute": chartId,
				"onevent": function(data){
					CF.pollUpdate(data, chartId);
				},
				"dx": dx,
				"dy": dy,
				"dir": direction,
				"event.type": CF.MOVE
			});
		} catch (e) {
			CF.showError(e.toString());
		}
	});

	svgElement.subscribe(CF.WHEEL, function(atx, aty, delta) {
		try {
			jsf.ajax.request(chartId, null, {
				"execute": chartId,
				"onevent": function(data){
					CF.pollUpdate(data, chartId);
				},
				"at.x": atx,
				"at.y": aty,
				"delta": delta,
				"event.type": CF.WHEEL
			});

			CF.registerForProgressive(chartId, progressive);
		} catch (e) {
			CF.showError(e.toString());
		}
	});

	svgElement.subscribe(CF.ZOOM, function(dx, dy, ux, uy) {
		try {
			jsf.ajax.request(chartId, null, {
				"execute": chartId,
				"onevent": function(data){
					CF.pollUpdate(data, chartId);
				},
				"down.x": dx,
				"down.y": dy,
				"up.x": ux,
				"up.y": uy,
				"event.type": CF.ZOOM
			});

			CF.registerForProgressive(chartId, progressive);
		} catch (e) {
			CF.showError(e.toString());
		}
	});

	var getRealX = function(evt) {
		return (evt.originalEvent.clientX-clientLeft)/clientWidth * viewWidth + viewLeft;
	}

	var getRealY = function(evt) {
		return (evt.originalEvent.clientY-clientTop)/clientHeight * viewHeight + viewTop;
	}

	var getRealDX = function(dx) {
		return dx/clientWidth * viewWidth;
	}

	var getRealDY = function(dy) {
		return dy/clientHeight * viewHeight;
	}

	var createVPath = function(x) {
		var buffer = [], i=0;
		buffer[i++]='M';
		buffer[i++]=x;
		buffer[i++]=' 0V';
		buffer[i++]=viewHeight;
		return buffer.join('');
	}

	var createHPath = function(y) {
		var buffer = [], i=0;
		buffer[i++]='M0 ';
		buffer[i++]=y;
		buffer[i++]='H';
		buffer[i++]=viewWidth;
		return buffer.join('');
	}

	var addPath = function(p) {
		var path = document.createElementNS(svgNS, 'svg:path');
		path.setAttribute('d',p);
		SVGRoot.appendChild(path);
		return path;
	}

	function MouseTracker () {
		this.vpath = null;
		this.vpath1 = null;
		this.hpath = null;
		this.hpath1 = null;
		this.active = false;
	};

	MouseTracker.prototype.start = function(evt) {
		mouseMoved = false;
		this.end();
		this.active = true;
		var x = getRealX(evt);
		var y = getRealY(evt);
		this.vpath = addPath(createVPath(x));
		this.vpath.setAttribute('style','fill:none;stroke:#999999;stroke-width:1px');
		this.vpath1 = addPath(createVPath(x));
		this.vpath1.setAttribute('style','fill:none;stroke:#999999;stroke-width:1px');
		this.hpath = addPath(createHPath(y));
		this.hpath.setAttribute('style','fill:none;stroke:#999999;stroke-width:1px;');
		this.hpath1 = addPath(createHPath(y));
		this.hpath1.setAttribute('style','fill:none;stroke:#999999;stroke-width:1px;');
	};

	MouseTracker.prototype.update = function(evt) {
		if ( this.active ) {
			var x = getRealX(evt);
			var y = getRealY(evt);
			this.vpath1.setAttribute('d',createVPath(x));
			this.hpath1.setAttribute('d',createHPath(y));
		}
	}

	MouseTracker.prototype.end = function() {
		this.active = false;
		if ( this.vpath && this.vpath.parentNode ) {
			SVGRoot.removeChild(this.vpath);
			SVGRoot.removeChild(this.vpath1);
			SVGRoot.removeChild(this.hpath);
			SVGRoot.removeChild(this.hpath1);
		}
	}

	svgElement.mousedown(function(evt) {
		if (evt.shiftKey) {
			mouseDown = true;
			downx = getRealX(evt);
			downy = getRealY(evt);
			mouseTracker.start(evt);
		}
	});

	svgElement.mousemove(function(evt) {
		if (evt.shiftKey && mouseDown) {
			var x = getRealX(evt);
			var y = getRealY(evt);
			if (x <= viewLeft || x >= viewRight || y <= viewTop || y >= viewBottom) {
				mouseTracker.end();
			} else {
				mouseTracker.update(evt);
			}

			if (!mouseMoved) {
				startTime = new Date().getTime();
				mouseMoved = true;
			}
		}
	});

	svgElement.mouseup(function(evt) {
		mouseDown = false;
		mouseTracker.end();

		if ( startTime == null ) {
			// we never moved
			return;
		}

		if (evt.shiftKey) {
			upx = getRealX(evt);
			upy = getRealY(evt);

			upTime = new Date().getTime();
			var diffTime = upTime - startTime;
			startTime = null;
			if (diffTime < 1000) {
				return;
			}

			$().publish(CF.ZOOM, [downx, downy, upx, upy]);
		}
	});

	var updated = true;
	svgElement.mousewheel(function(evt, delta) {
		if ( updated ) {
			updated = false;
			var x = getRealX(evt);
			var y = getRealY(evt);
			svgElement.css('cursor','busy');
			$().publish(CF.WHEEL, [x,y,evt.originalEvent.wheelDelta]);
		}
		evt.stopPropagation();
		evt.preventDefault();
	});

	var dragDx = 0;
	var dragDy = 0;
	svgElement.drag(function(evt, dd) {
		if (updated) {
			dragDx += dd.deltaX;
			dragDy += dd.deltaY;
		}
		evt.stopPropagation();
		evt.preventDefault();
	});

	svgElement.drop(function() {
		if (updated) {
			updated = false;
			$().publish(CF.MOVE, ['', getRealDX(dragDx), getRealDY(dragDy)]);
		}
	});

	var reset = function () {
		svgElement.css('cursor','default');
		updated = true;
		dragDx = 0;
		dragDy = 0;
	};

	svgElement.subscribe(CF.UPDATED, reset);
	svgElement.subscribe(CF.ERRORED, reset);
};