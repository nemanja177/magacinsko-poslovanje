import { TestBed } from '@angular/core/testing';

import { RadnikService } from './radnik.service';

describe('RadnikService', () => {
  let service: RadnikService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RadnikService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
