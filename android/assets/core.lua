gameAPI = require('scripting/JavaAPI')

stageReference = nil;

componentFactory = nil;
objectFactory = nil;
actionFactory = nil;

actionManager = nil;
componentManager = nil;
scriptingEngine = nil;

SCREEN_WIDTH = 1024
SCREEN_HEIGHT = 768

function registerStage(stage)
	stageReference = stage;
	
	objectFactory = stage:getObjectFactory();
	componentFactory = stage:getComponentFactory();
	actionFactory = stage:getActionFactory();
	
	actionManager = stage:getActionManager();
	componentManager = stage:getComponentManager();
	scriptingEngine = stage:getScriptingEngine();
end

-- Duration in seconds
-- Creates a ScriptWaitAction, gets the actionManager to begin it
-- Then stops the thread which called this.
function wait(duration, callBack)

	--The old, Java-side version:
	--gameAPI.wait(waitTime, stageReference, callBack);
	
	waitAction = actionFactory:scriptWaitAction(duration, callBack);
	actionManager:beginAction(waitAction);
	scriptingEngine:yieldThread();
end

-- Works on any component whose GameObject whole has a PhysicsComponent
function move(actorComponent, distance, speed, xDirection, yDirection)
	moveAction = actionFactory:moveAction(actorComponent, distance, speed, 
		xDirection, yDirection);
	
	actionManager:beginAction(moveAction);
end


