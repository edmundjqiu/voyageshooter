pathTable = {
	function (scriptComponent) scriptComponent:move() end

}

function levelAction( thisThreadReference )
	spawnEnemy(0, 0, "BASIC");
	gameAPI.wait(3.0, stageReference, thisThreadReference);
	spawnEnemy(0, 50, "BASIC");
	gameAPI.wait(3.0, stageReference, thisThreadReference);
	spawnEnemy(0, 100, "BASIC");

end

function getLevel1Paths()
	return pathTable
end
