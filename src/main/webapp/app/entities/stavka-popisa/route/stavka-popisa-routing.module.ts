import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { StavkaPopisaComponent } from '../list/stavka-popisa.component';
import { StavkaPopisaDetailComponent } from '../detail/stavka-popisa-detail.component';
import { StavkaPopisaUpdateComponent } from '../update/stavka-popisa-update.component';
import { StavkaPopisaRoutingResolveService } from './stavka-popisa-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const stavkaPopisaRoute: Routes = [
  {
    path: '',
    component: StavkaPopisaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StavkaPopisaDetailComponent,
    resolve: {
      stavkaPopisa: StavkaPopisaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StavkaPopisaUpdateComponent,
    resolve: {
      stavkaPopisa: StavkaPopisaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StavkaPopisaUpdateComponent,
    resolve: {
      stavkaPopisa: StavkaPopisaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(stavkaPopisaRoute)],
  exports: [RouterModule],
})
export class StavkaPopisaRoutingModule {}
