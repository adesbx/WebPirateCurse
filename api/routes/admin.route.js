import { Router } from 'express';
import express from 'express';
import { postInitZRR, postSpawnFlask, putTTL } from '../services/admin.js';

const router = new Router();
//pour avoir le body
router.use(express.json());

router.post('/initZRR', async (req, res, next) => {
  let options = { }
  options.latLng1 = req.body.latLng1;
  options.latLng2 = req.body.latLng2;
  
  try {
    const result = await postInitZRR(options, req.headers.origin, req.headers.authentication);
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

router.put('/modifyTTL', async (req, res, next) => {
  let options = { }
  options.ttl = req.body.ttl;
  
  try {
    const result = await putTTL(options, req.headers.origin, req.headers.authentication);
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

router.post('/spawnFlask', async (req, res, next) => {
  let options = { }
  options.latLng = req.body.latLng;
  
  try {
    const result = await postSpawnFlask(options, req.headers.origin, req.headers.authentication);
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