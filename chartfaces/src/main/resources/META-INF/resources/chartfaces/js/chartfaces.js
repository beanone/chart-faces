function Clone() { }
/**
 * This method clones the passed in object and returns the cloned. The cloned
 * object will share the exact same attributes until the caller alters it. Such
 * a clone is memory efficient because changes to attributes (customization)
 * of the cloned object won't affect the original but changes to the original
 * will be seen by the cloned if the modified attribute has not been customized
 * by the cloned.
 *
 * @param obj the object to be cloned.
 * @returns a clone of the passed in.
 */
function clone(obj) {
	if ( typeof obj == 'object' ) {
		Clone.prototype = obj;
		return new Clone();
	} else {
		return obj;
	}
}

/**
* Function : dump()
* Arguments: The data - array,hash(associative array),object
*    The level - OPTIONAL
* Returns  : The textual representation of the array.
* This function was inspired by the print_r function of PHP.
* This will accept some data as the argument and return a
* text that will be a more readable version of the
* array/hash/object that is given.
*/
function dump(arr,level) {
	var dumped_text = "";
	if(!level){level = 0;}

	//The padding given at the beginning of the line.
	var level_padding = "";
	for(var j=0;j<level+1;j++) {level_padding += "    ";}

	if(typeof(arr) == 'object') { //Array/Hashes/Objects
	 for(var item in arr) {
	  var value = arr[item];

	  if(typeof(value) == 'object') { //If it is an array,
	   dumped_text += level_padding + "'" + item + "' ...\n";
	   dumped_text += dump(value,level+1);
	  } else {
	   dumped_text += level_padding + "'" + item + "' => \"" + value + "\"\n";
	  }
	 }
	} else { //Stings/Chars/Numbers etc.
	 dumped_text = "===>"+arr+"<===("+typeof(arr)+")";
	}
	return dumped_text;
}

/**
 * This object helps one to deserialize a JavaScript-Array-Notation (Jsan). Jsan is an
 * extension to Json. It is invented here to represent an array of Json objects to save memory
 * or communication footprint. Here is an example of a Jsan String:
 *
 * {
 * 	"use": {
 *  	"style":'myStyle',
 *  	"xlink:href":"#mySymbol",
 *  	"x":['0','1'],
 *  	"y":['0','1'],
 *  	"width":['1','2'],
 *  	"height":['1','2'],
 *  }
 * }
 *
 * We can see that this is simply a Json object. However, it actually is used to represent an
 * array of Json objects corresponding to the following XML tags:
 *
 * &lt;use style="myStyle" xlink:href="#mySymbol" x="0" y="0" width="1" height="1"/&gt;
 * &lt;use style="myStyle" xlink:href="#mySymbol" x="1" y="1" width="2" height="2"/&gt;
 *
 * In general, a Jsan String takes the following form:
 *
 * {
 * 	"aName": {
 * 		attr1:value1,
 * 		attr2:value2, // more attributes...
 * 		arr1:[val11,val12,...],
 * 		arr2:[val21,val22,...], // more arrays...
 * 	}
 * }
 *
 * where, attr1, attr2, arr1, arr2, ... are all quoted strings, and value1, value2, val11, val12,
 * val21, val22, ..., etc., are all either quoted strings or Jsan objects. Also, the length of
 * the arrays arr1, arr2, ..., etc., must be the same, otherwise an error will be thrown.
 *
 * This object can be used either by invoking its toObject() or get(index) method. The former
 * de-serializes the Json object in one call and thus requires more memory. The later, one
 * element at a time. The size() method returns the elements count. Assume that Json is the above
 * mentioned Json object for the "use" tag, the following is how one would invoke the toObject()
 * method:
 *
 * new Jsan(json).toObject();
 *
 * And here is how one would use the potentially memory efficient get(index) method:
 *
 * new Jsan(json.use).get(0);
 *
 * @param json a Json object.
 * @returns {Jsan}
 */
function Jsan(json) {
  this._size = 0; // number of elements that we can iterate
  this._dataArray = new Object(); // the data that is different from instance to instance
  this._base = new Object(); // the base for every instance of the objects
  this._initialized = false; // whether the Jsans in this.base are expanded

  if ( json ) {
    if ( (typeof json) != "object" ) {
    	throw new Error("Premitive data types are not supported!");
    } else {
      var v;
 	    for ( var k in json ) {
        v = json[k];
        if ( v instanceof Array ) {
          if ( v.length == 0 ) {
            continue;
          }

          if ( this._size == 0 ) {
            this._size = v.length;
          } else if ( v.length != this._size ) {
              throw new Error("Array length inconsistent!");
          }
          if ( (typeof v[0]) == "object" ) {
            this._dataArray[k] = new Array();
            for ( var i = 0; i < v.length; i++ ) {
              this._dataArray[k][i] = new Jsan(v[i]);
            }
          } else {
            this._dataArray[k]=v;
          }
        } else if ( (typeof v) == "object" ) {
          this._base[k]=new Jsan(v);
        } else {
	        this._base[k]=v;
	      }
      }
    }

    if ( this._size == 0 ) {
      this._size = 1;
    }
  }
}

/**
 * Deserializes the enclosed Json object and returns an array of Json objects.
 *
 * @returns an object array or a single object (not an array) if there are only
 * one element in the to be returned array.
 */
Jsan.prototype.toObject = function () {
  var result;
  if ( this._size <= 1 ) {
    result = this.get(0);
  } else {
    result = new Array();
    for ( var i = 0; i < this._size; i++ ) {
	    result[i] = this.get(i);
    }
  }

  return result;
};


/**
 * Returns the element at the given index.
 *
 * @param index an index value between 0 and size().
 * @returns the element at the given index.
 */
Jsan.prototype.get = function(index) {
	// check index out of bound
	if ( index < 0 || index > this._size - 1 ) {
	    throw new Error ("Index out of bound: " + index + ". The max index is " + (this._size - 1));
	}

	var o;
	// lazily initialize the object member
	if ( !this._initialized ) {
		for ( var k in this._base ) {
			o = this._base[k];
			if ( o instanceof Jsan ) {
				this._base[k] = o.toObject();
			}
		}
		this._initialized = true;
	}

	// clone the base to create our new instance
	// clone is used for CPU and memory efficiency
	var result = clone(this._base);
	for ( var k in this._dataArray ) {
		o = this._dataArray[k][index];
		if ( o instanceof Jsan ) {
			// initialize the array member only when object instance is created
			o = o.toObject();
		}
		result[k] = o;
	}

	return result;
};

/**
 * @returns the number of elements.
 */
Jsan.prototype.size = function() {
	return this._size;
};

function StringBuffer() {
  this.buffer = [];
}

StringBuffer.prototype.append = function append(string) {
	this.buffer.push(string);
	return this;
};

StringBuffer.prototype.toString = function toString() {
  return this.buffer.join("");
};

/**
 * Turns a single tag (as opposed to an array of tags) into XML.
 *
 * @param obj the object to be rendered to a XML tag.
 * @param tag name of a XML tag.
 * @returns the rendered tag XML string.
 */
function toTagXML(tag, obj) {
	var sb = new StringBuffer();
	sb.append("<").append(tag);

	// render attributes
	for ( var k in obj ) {
		if ( typeof obj[k] == "string" ) {
			sb.append(" ").append(k).append("=\"").append(obj[k]).append("\"");
		}
	}

	// render children
	var count = 0;
	for ( var k in obj ) {
		if ( typeof obj[k] == "object" ) {
			if ( count == 0 ) {
				sb.append(">");
			}
			sb.append("\n");
			toXML(k, obj[k], sb);
			count++;
		}
	}

	if ( count == 0 ) {
		sb.append("/>"); // an empty tag
	} else {
		sb.append("\n").append("</").append(tag).append(">").append("\n"); // a tag with children
	}

	return sb.toString();
}

/**
 * Render the passed in object to a tag or object array to an array of tags.
 *
 * @param tag name of a XML tag.
 * @param obj the object or object array to be rendered to a XML tag or an array of XML tags.
 * @param sb a StringBuffer that the rendered result will be stored in.
 */
function toXML(tag, obj, sb) {
	if ( obj instanceof Array ) {
		for ( var i = 0; i < obj.length; i++ ) {
			sb.append(toTagXML(tag, obj[i]));
		}
	} else {
		sb.append(toTagXML(tag, obj));
	}
}

var svgNS = "http://www.w3.org/2000/svg";
var xlinkNS = "http://www.w3.org/1999/xlink";

/**
 * Insert a single new node (as opposed to an array of nodes) corresponding to
 * the passed in object into the passed in parent node.
 *
 * @param ns name-space of the tag for the node (except for the href tag).
 * @param obj the object to be rendered to a XML tag.
 * @param tag name of a XML tag.
 * @param parent the parent SVG element the new node is to be inserted into the
 * 		passed in parent node.
 */
function insertTagToDocument(ns, tag, obj, parent, base) {
	if (obj instanceof Array) {
		for ( var k in obj ) {
			base = insertTagToDocument(ns, tag, obj[k], parent, base);
		}
	} else {
		for ( var k in obj ) {
			// Just to check whether there are children for the element
			// we are going to create. If yes, don't use clone.
			if ( typeof obj[k] == "object" ) {
				base = null;
				break;
			}
		}
		var e;
		var tagName = tag.split('.')[0];
		if (tagName=="text") {
			e = createElementNS(document, ns, tagName);
		} else if (base) {
			e = clone(base);
		} else {
			e = createElementNS(document, ns, tagName);
		}

		// render attributes
		for ( var k in obj ) {
			if ( typeof obj[k] == "string" ) {
				if (k.indexOf(":") > 0) {
					e.setAttributeNS(xlinkNS, k, obj[k]);
				} else if (k == "innerText") {
					e.textContent = obj[k];
				} else {
					e.setAttribute(k, obj[k]);
				}
			}
		}

		// render children
		for ( var k in obj ) {
			if ( typeof obj[k] == "object" ) {
				insertTagToDocument(ns, k, obj[k], e, null);
			}
		}

		parent.appendChild(e);

		return e;
	}
}

function createElementNS(document, ns, tagName) {
	return document.createElementNS(svgNS, ns+':'+tagName);
}

/**
 * Insert an array of new nodes (each may contain children nodes) corresponding
 * to the passed in object array to the passed in parent node.
 *
 * @param ns name-space of the tags for the nodes (except for the href tag).
 * @param tag name of the top-most node of all nodes to be created.
 * @param obj the object or object array the corresponding nodes of each are to be
 * 		inserted into the passed in parent node.
 * @param parent the parent SVG element the new nodes are to be inserted into.
 */
function insertToDocument(ns, tag, obj, parent) {
	if ( obj instanceof Array ) {
		for ( var i = 0; i < obj.length; i++ ) {
			insertTagToDocument(ns, tag, obj[i], parent, null);
		}
	} else {
		insertTagToDocument(ns, tag, obj, parent, null);
	}
}
