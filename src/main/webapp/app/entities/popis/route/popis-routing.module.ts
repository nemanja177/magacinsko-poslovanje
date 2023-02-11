import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PopisComponent } from '../list/popis.component';
import { PopisDetailComponent } from '../detail/popis-detail.component';
import { PopisUpdateComponent } from '../update/popis-update.component';
import { PopisRoutingResolveService } from './popis-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const popisRoute: Routes = [
  {
    path: '',
    component: PopisComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PopisDetailComponent,
    resolve: {
      popis: PopisRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PopisUpdateComponent,
    resolve: {
      popis: PopisRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PopisUpdateComponent,
    resolve: {
      popis: PopisRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(popisRoute)],
  exports: [RouterModule],
})
export class PopisRoutingModule {}
