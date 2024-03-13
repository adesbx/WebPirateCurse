const express = require('express');
const resources = require('../services/resources');
const router = new express.Router();
 
router.get('/', async (req, res, next) => {
  let options = { 
  };


  try {
    const result = await resources.getResources(options);
    res.status(result.status || 200).send(result.data);
  }
  catch (err) {
    return res.status(500).send({
      error: err || 'Something went wrong.'
    });
  }
});
 
router.post('/:resourceId', async (req, res, next) => {
  let options = { 
    "resourceId": req.params.resourceId,
  };

  options.operationType = req.body;

  try {
    const result = await resources.postResourceId(options);
    res.status(result.status || 200).send(result.data);
  }
  catch (err) {
    return res.status(500).send({
      error: err || 'Something went wrong.'
    });
  }
});
 
router.put('/:resourceId/position', async (req, res, next) => {
  let options = { 
    "resourceId": req.params.resourceId,
  };

  options.latLng = req.body;

  try {
    const result = await resources.putResourceIdPosition(options);
    res.status(result.status || 200).send(result.data);
  }
  catch (err) {
    return res.status(500).send({
      error: err || 'Something went wrong.'
    });
  }
});

module.exports = router;