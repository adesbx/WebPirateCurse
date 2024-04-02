import { Router } from 'express';
import express from 'express'
import { getResources, postResourceId, putResourceIdPosition } from '../services/resources.js';
const router = new Router();

//pour avoir le body
router.use(express.json());

router.get('/', async (req, res, next) => {
  let options = { 
  };

  const origin = req.protocol + "://" + req.headers.host
  try {
    const result = await getResources(options, origin, req.headers.authentication);
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
    const result = await postResourceId(options, req.headers.origin, req.headers.authentication);
    if (result.status == 204) {
      return res.status(result.status).send();
    } else {
      return res.status(result.status).send(result.data);
    }
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
    const result = await putResourceIdPosition(options, req.headers.origin, req.headers.authentication);
    if (result.status == 204) {
      return res.status(result.status).send();
    } else {
      return res.status(result.status).send(result.data);
    }
  }
  catch (err) {
    return res.status(500).send({
      error: err || 'Something went wrong.'
    });
  }
});

export default router;