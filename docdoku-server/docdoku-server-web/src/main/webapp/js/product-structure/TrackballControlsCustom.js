/**
 * @author Florent Suc
 */

THREE.TrackballControlsCustom = function ( object, domElement ) {

    THREE.EventDispatcher.call( this );

    var _this = this,
        STATE = { NONE : -1, PAN : 0, ZOOM : 1, ROTATE : 2}

    this.object = object;
    this.domElement = ( domElement !== undefined ) ? domElement : document;

    // API

    this.enabled = true;

    this.screen = { width: window.innerWidth, height: window.innerHeight, offsetLeft: 0, offsetTop: 0 };
    this.radius = ( this.screen.width + this.screen.height ) / 4;

    this.rotateSpeed = 1.0;
    this.zoomSpeed = 1.2;
    this.panSpeed = 0.3;

    this.noRotate = false;
    this.noZoom = false;
    this.noPan = false;

    this.staticMoving = false;
    this.dynamicDampingFactor = 0.2;

    this.minDistance = 0;
    this.maxDistance = Infinity;

    this.keys = [ 65 /*A*/, 83 /*S*/, 68 /*D*/ ];

    // To change according to the bounding box (todo)
    this.stopZoomingFactor = 5;

    // touch vars

    var startY;
    var touchZoomingFactor = 5;

    // internals

    this.target = new THREE.Vector3();

    var lastPosition = new THREE.Vector3();

    var _keyPressed = false,
        _state = STATE.NONE,

        _eye = new THREE.Vector3(),

        _rotateStart = new THREE.Vector3(),
        _rotateEnd = new THREE.Vector3(),

        _zoomStart = new THREE.Vector2(),
        _zoomEnd = new THREE.Vector2(),

        _panStart = new THREE.Vector2(),
        _panEnd = new THREE.Vector2();

    // events

    var changeEvent = { type: 'change' };


    // methods

    this.handleEvent = function ( event ) {

        if ( typeof this[ event.type ] == 'function' ) {

            this[ event.type ]( event );

        }

    };

    this.getMouseOnScreen = function ( clientX, clientY ) {

        return new THREE.Vector2(
            ( clientX - _this.screen.offsetLeft ) / _this.radius * 0.5,
            ( clientY - _this.screen.offsetTop ) / _this.radius * 0.5
        );

    };

    this.getMouseProjectionOnBall = function ( clientX, clientY ) {

        var mouseOnBall = new THREE.Vector3(
            ( clientX - _this.screen.width * 0.5 - _this.screen.offsetLeft ) / _this.radius,
            ( _this.screen.height * 0.5 + _this.screen.offsetTop - clientY ) / _this.radius,
            0.0
        );

        var length = mouseOnBall.length();

        if ( length > 1.0 ) {

            mouseOnBall.normalize();

        } else {

            mouseOnBall.z = Math.sqrt( 1.0 - length * length );

        }

        _eye.copy( _this.object.position ).sub( _this.target );

        var projection = _this.object.up.clone().setLength( mouseOnBall.y );
        projection.add( _this.object.up.clone().cross( _eye ).setLength( mouseOnBall.x ) );
        projection.add( _eye.setLength( mouseOnBall.z ) );

        return projection;

    };

    this.rotateCamera = function () {

        var angle = Math.acos( _rotateStart.dot( _rotateEnd ) / _rotateStart.length() / _rotateEnd.length() );

        if ( angle ) {

            var axis = ( new THREE.Vector3() ).crossVectors( _rotateStart, _rotateEnd ).normalize(),
                quaternion = new THREE.Quaternion();

            angle *= _this.rotateSpeed;

            quaternion.setFromAxisAngle( axis, -angle );

            _eye.applyQuaternion( quaternion );
            _this.object.up.applyQuaternion( quaternion );

            _rotateEnd.applyQuaternion( quaternion );

            if ( _this.staticMoving ) {

                _rotateStart = _rotateEnd;

            } else {

                quaternion.setFromAxisAngle( axis, angle * ( _this.dynamicDampingFactor - 1.0 ) );
                _rotateStart.applyQuaternion( quaternion );

            }

        }

    };

    this.zoomCamera = function() {
        var factor = 1.0 + ( _zoomEnd.y - _zoomStart.y ) * _this.zoomSpeed;
        this.zoomCameraOperation(factor);
    };

    this.zoomCameraWheel = function(wheelDeltaY) {
        var factor = 1.0 + ( wheelDeltaY ) * _this.zoomSpeed;
        this.zoomCameraOperation(factor);
    };

    this.zoomCameraOperation = function (factor) {

        if ( factor !== 1.0 && factor > 0.0 ) {

            if(_eye.x*factor > this.stopZoomingFactor || _eye.y*factor > this.stopZoomingFactor || _eye.z*factor > this.stopZoomingFactor) {

                _eye.multiplyScalar( factor );

                if ( _this.staticMoving ) {

                    _zoomStart = _zoomEnd;

                } else {

                    _zoomStart.y += ( _zoomEnd.y - _zoomStart.y ) * this.dynamicDampingFactor;

                }

            }

        }

        this.manualUpdate();

    };

    this.panCamera = function () {
        var mouseChange = _panEnd.clone().sub( _panStart );
        this.panCameraOperation(mouseChange);
    };

    this.panCameraOperation = function(mouseChange) {
        if ( mouseChange.lengthSq() ) {

            mouseChange.multiplyScalar( _eye.length() * _this.panSpeed );

            var pan = _eye.clone().cross( _this.object.up ).setLength( mouseChange.x );
            pan.add( _this.object.up.clone().setLength( mouseChange.y ) );

            _this.object.position.add( pan );
            _this.target.add( pan );

            if ( _this.staticMoving ) {

                _panStart = _panEnd;

            } else {

                _panStart.add( mouseChange.sub( _panEnd, _panStart ).multiplyScalar( _this.dynamicDampingFactor ) );

            }

        }
    };

    this.checkDistances = function () {

        if ( !_this.noZoom || !_this.noPan ) {

            if ( _this.object.position.lengthSq() > _this.maxDistance * _this.maxDistance ) {

                _this.object.position.setLength( _this.maxDistance );

            }

            if ( _eye.lengthSq() < _this.minDistance * _this.minDistance ) {

                _this.object.position.add( _this.target, _eye.setLength( _this.minDistance ) );

            }

        }

    };

    this.update = function () {

        _eye.copy( _this.object.position ).sub( _this.target );

        if ( !_this.noRotate ) {

            _this.rotateCamera();

        }

        if ( !_this.noZoom ) {

            _this.zoomCamera();

        }

        if ( !_this.noPan ) {

            _this.panCamera();

        }

        _this.object.position.addVectors( _this.target, _eye );

        _this.checkDistances();

        _this.object.lookAt( _this.target );

        if ( lastPosition.distanceTo( _this.object.position ) > 0 ) {

            _this.dispatchEvent( changeEvent );

            lastPosition.copy( _this.object.position );

        }

    };

    this.manualUpdate = function () {
        _this.object.position.addVectors( _this.target, _eye );

        _this.checkDistances();

        _this.object.lookAt( _this.target );

        if ( lastPosition.distanceTo( _this.object.position ) > 0 ) {

            _this.dispatchEvent( changeEvent );

            lastPosition.copy( _this.object.position );

        }
    };

    // listeners

    function keydown( event ) {

        if ( ! _this.enabled ){
            return;
        }

        if ( _state !== STATE.NONE ) {

            return;

        } else if ( event.keyCode === _this.keys[ STATE.ROTATE ] && !_this.noRotate ) {

            _state = STATE.ROTATE;

        } else if ( event.keyCode === _this.keys[ STATE.ZOOM ] && !_this.noZoom ) {

            _state = STATE.ZOOM;

        } else if ( event.keyCode === _this.keys[ STATE.PAN ] && !_this.noPan ) {

            _state = STATE.PAN;

        }

        if ( _state !== STATE.NONE ) {

            _keyPressed = true;

        }

    }

    function keyup( event ) {

        if ( ! _this.enabled ){
            return;
        }

        if ( _state !== STATE.NONE ) {

            _state = STATE.NONE;

        }

    }

    function mouseup( event ) {

        if ( ! _this.enabled ){
            return;
        }

        event.preventDefault();
        event.stopPropagation();

        _state = STATE.NONE;

        document.removeEventListener( 'mouseup', mouseup, false );

        _this.bindMouseWheel();

    }

    function mousemove( event ) {

        if ( ! _this.enabled ){
            return;
        }

        if ( _keyPressed ) {

            _rotateStart = _rotateEnd = _this.getMouseProjectionOnBall( event.clientX, event.clientY );
            _zoomStart = _zoomEnd = _this.getMouseOnScreen( event.clientX, event.clientY );
            _panStart = _panEnd = _this.getMouseOnScreen( event.clientX, event.clientY );

            _keyPressed = false;

        }

        if ( _state === STATE.NONE ) {

            return;

        } else if ( _state === STATE.ROTATE && !_this.noRotate ) {

            _rotateEnd = _this.getMouseProjectionOnBall( event.clientX, event.clientY );

        }  else if ( _state === STATE.PAN && !_this.noPan ) {

            _panEnd = _this.getMouseOnScreen( event.clientX, event.clientY );

        }

    }

    function mousedown( event ) {

        if ( ! _this.enabled ){
            return;
        }

        event.preventDefault();
        event.stopPropagation();

        if ( _state === STATE.NONE ) {

            _state = event.button;

            if ( _state === STATE.ROTATE && !_this.noRotate ) {

                _rotateStart = _rotateEnd = _this.getMouseProjectionOnBall( event.clientX, event.clientY );

            }  else if ( !this.noPan ) {

                _panStart = _panEnd = _this.getMouseOnScreen( event.clientX, event.clientY );

            }

        }

        domElement.addEventListener( 'mouseup', mouseup, false );

        _this.unbindMouseWheel();

    }

    function mousewheel(event) {

        event.preventDefault();
        event.stopPropagation();

        if ( event.wheelDelta ) // webkit, opera, IE9
            _this.zoomCameraWheel(-event.wheelDeltaY * 0.0001);
        else if ( event.detail ) // firefox
            _this.zoomCameraWheel(event.detail * 0.0015);
    }

    function touchstart( event ) {

        var touch = event.touches[0];
        event['clientX'] = touch.clientX;
        event['clientY'] = touch.clientY;

        if (event.touches.length == 1) {

            // 1 finger : rotate
            event['button'] = 2;
            mousedown( event );

        } else if (event.touches.length == 2) {

            // 2 fingers : zoom in / out
            startY = touch.clientY;
            _state = STATE.ZOOM;

        } else if (event.touches.length == 3) {

            // 3 fingers : pan
            event['button'] = 0;
            mousedown( event );

        }

        document.domElement.addEventListener( 'touchend', mouseup, false );
    }

    function touchmove( event ) {

        var touch = event.touches[0];
        event['clientX'] = touch.clientX;
        event['clientY'] = touch.clientY;

        if (_state === STATE.ZOOM) {

            // 2 fingers : zoom in / out
            event.wheelDeltaY = (startY - touch.clientY) * touchZoomingFactor;
            mousewheel(event);
            startY = touch.clientY;

        } else {

            // 3 fingers : pan
            mousemove( event );

        }
    }

    function touchend( event ) {

        if ( ! _this.enabled ){
            return;
        }

        event.preventDefault();
        event.stopPropagation();

        _state = STATE.NONE;

        document.removeEventListener( 'touchend', touchend, false );

    }

    this.bindMouseWheel = function() {
        this.domElement.addEventListener('DOMMouseScroll', mousewheel, false);
        this.domElement.addEventListener('mousewheel', mousewheel, false);
    };

    this.unbindMouseWheel = function() {
        this.domElement.removeEventListener('DOMMouseScroll', mousewheel, false);
        this.domElement.removeEventListener('mousewheel', mousewheel, false);
    };

    this.destroyControl = function() {
        this.domElement.removeEventListener( 'contextmenu', function ( event ) { event.preventDefault(); }, false );

        this.domElement.removeEventListener( 'mousemove', mousemove, false );
        this.domElement.removeEventListener( 'mousedown', mousedown, false );

        window.removeEventListener( 'keydown', keydown, false );
        window.removeEventListener( 'keyup', keyup, false );

        this.unbindMouseWheel();

        this.domElement.removeEventListener( 'touchmove', touchmove, false );
        this.domElement.removeEventListener( 'touchstart', touchstart, false );
    };

    this.domElement.addEventListener( 'contextmenu', function ( event ) { event.preventDefault(); }, false );

    this.domElement.addEventListener( 'mousemove', mousemove, false );
    this.domElement.addEventListener( 'mousedown', mousedown, false );

    window.addEventListener( 'keydown', keydown, false );
    window.addEventListener( 'keyup', keyup, false );

    this.bindMouseWheel();

    this.domElement.addEventListener( 'touchmove', touchmove, false );
    this.domElement.addEventListener( 'touchstart', touchstart, false );
};