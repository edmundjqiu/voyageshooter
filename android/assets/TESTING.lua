
function bulletBehavior(threadHandle, scriptComponent)
	-- Basically, repeatedly check every
	continueChecking = true;
	while continueChecking do
		--wait(0.5, threadHandle);
		
		xLoc = scriptComponent:getXLocation();
		yLoc = scriptComponent:getYLocation();
		--gameAPI.print("("..xLoc.." "..yLoc..")");
		
		xRange = xLoc > SCREEN_WIDTH + 50 or xLoc < -50;
		yRange = yLoc > SCREEN_HEIGHT + 50 or yLoc < -50;
		if xRange or yRange then
			scriptComponent:gameObjectDeath();
			continueChecking = false;
		end
		
		
		--continueChecking = scriptComponent:isAlive();
	end

end

function enemyBehavior(threadHandle, scriptComponent)
	-- Basically, repeatedly check every
	
	angle = 0;
	
	
	-- how long between firing
	delaySeconds = 0.2;
	-- How much the thing rotates in degrees
	angleIncrement = 25;
	
	-- How many bullets are shot in a circle
	bulletsPerShot = 12;
	-- How fast each bullet travels
	bulletVelocity = 50;

	
	wait(5, threadHandle);

	--[[
	delaySeconds = 0.5;
	angleIncrement = 8;
	bulletsPerShot = 8;
	bulletVelocity = 50;	
	]]--
	
	while true do
	
		timesShot = 0;
	
		while timesShot < 5 do
			wait(delaySeconds, threadHandle);
			
			xLoc = scriptComponent:getXLocation();
			yLoc = scriptComponent:getYLocation();
			
			for i = 0, 360, 360/bulletsPerShot do
				shotAngle = angle + i;
				angleInRad = math.rad(shotAngle);
				xVelocity = math.cos(angleInRad) * bulletVelocity;
				yVelocity = math.sin(angleInRad) * bulletVelocity;
				
				scriptComponent:spawnBullet(xLoc, yLoc, xVelocity, yVelocity);
			end
			
			angle = angle + angleIncrement;
			
			timesShot = timesShot + 1;
		end
		
		--move(actorComponent, distance, speed, xDirection, yDirection)
		--move(scriptComponent, 100, 50, 0, 1);
		--move(scriptComponent, 100, 50, 1, 0);
		
		--wait(2, threadHandle);
		--move(scriptComponent, 100, 50, 0, -1);
		--move(scriptComponent, 100, 50, -1, 0);
		
		--return
		
	end
	
	
end


function enemyBehavior0(threadHandle, scriptComponent)
	-- Basically, repeatedly check every
	continueChecking = true;
	
	angle = 0;
	-- delaySeconds = 0.020;
	-- angleIncrement = 8;
	-- bulletsPerShot = 10;
	-- bulletVelocity = 50;
	
	
	delaySeconds = 0.5;
	angleIncrement = 8;
	bulletsPerShot = 10;
	bulletVelocity = 50;
	
	
	while continueChecking do
		wait(delaySeconds, threadHandle);
		
		xLoc = scriptComponent:getXLocation();
		yLoc = scriptComponent:getYLocation();
		
		for i = 0, 360, 360/bulletsPerShot do
			shotAngle = angle + i;
			angleInRad = math.rad(shotAngle);
			xVelocity = math.cos(angleInRad) * bulletVelocity;
			yVelocity = math.sin(angleInRad) * bulletVelocity;
			
			scriptComponent:spawnBullet(xLoc, yLoc, xVelocity, yVelocity);
		end
		
		angle = angle + angleIncrement;
		
		
	end

end
